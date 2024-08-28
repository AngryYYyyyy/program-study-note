package com.mashibing.serviceprice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.internalcommon.dto.PriceRule;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cpf
 * @since 2022-10-11
 */
@Repository
public interface PriceRuleMapper extends BaseMapper<PriceRule> {

}
