package com.mashibing.serviceauth.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.remote.ServicePassengerUserClient;
import com.mashibing.internalcommon.remote.ServiceVefificationcodeClient;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.DriverUserExistsResponse;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import com.mashibing.internalcommon.responese.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceVefificationcodeClient serviceVefificationcodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 生成验证码
     * @return
     */
    public ResponseResult generatorCode(String phone , String identity){

        if (StringUtils.equalsIgnoreCase(identity,IdentityConstants.DRIVER_IDENTITY)){
            // 查询 service-driver-user，该手机号的司机是否存在
            ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriver(phone);
            DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
            int ifExists = data.getIfExists();
            if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS){
                return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITST.getCode(),CommonStatusEnum.DRIVER_NOT_EXITST.getValue());
            }
            log.info(phone+" 的司机存在");

        }




        // 调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVefificationcodeClient.getNumberCode(6);
        String numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("手机号："+ phone + "收到的验证码："+numberCode);

        // 存入redis
        // key,value,过期时间
        String key = RedisPrefixUtils.generatorKeyByPhone(phone, identity) ;
        // 存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联
        return ResponseResult.success("");

    }

    /**
     * 校验验证码
     * @param phone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String phone , String verificationCode , String identity){
        // 根据手机号，去redis读取验证码
        // 生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(phone,identity) ;
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value："+codeRedis);

        stringRedisTemplate.delete(key);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        if(StringUtils.equalsIgnoreCase(identity,IdentityConstants.PASSENGER_IDENTITY)){
            // 判断原来是否有用户，并进行对应的处理
            VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
            verificationCodeDTO.setPassengerPhone(phone);
            try {
                servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
            } catch (RuntimeException e){
                e.printStackTrace();
                return ResponseResult.fail(CommonStatusEnum.CALL_USER_ADD_ERROR.getCode(),CommonStatusEnum.CALL_USER_ADD_ERROR.getValue());
            }
        }else if(StringUtils.equalsIgnoreCase(identity,IdentityConstants.DRIVER_IDENTITY)){


        }


        // 颁发令牌，不应该用魔法值，用常量
        String accessToken = JwtUtils.generatorToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(phone, identity ,TokenConstants.REFRESH_TOKEN_TYPE);


        // 开启redis事务 支持
        stringRedisTemplate.setEnableTransactionSupport(true);
        SessionCallback<Boolean> callback = new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                // 事务开始
                stringRedisTemplate.multi();
                try {
                    // 将token存到redis当中
                    String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone , identity, TokenConstants.ACCESS_TOKEN_TYPE);
                    operations.opsForValue().set(accessTokenKey , accessToken , 30, TimeUnit.DAYS);
                    // 手动加异常
                    // int i = 1/0;
                    String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone , identity , TokenConstants.REFRESH_TOKEN_TYPE);
                    operations.opsForValue().set(refreshTokenKey , refreshToken , 31, TimeUnit.DAYS);
                    operations.exec();
                    return true;
                }catch (Exception e){
                    operations.discard();
                    return false;
                }

            }
        };
        Boolean execute = stringRedisTemplate.execute(callback);
        System.out.println("事务提交or回滚："+execute);
        if (execute){
            // 响应
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);
            return ResponseResult.success(tokenResponse);
        }else {
            return ResponseResult.fail(CommonStatusEnum.CHECK_CODE_ERROR.getCode(),CommonStatusEnum.CHECK_CODE_ERROR.getValue());
        }
    }
}
