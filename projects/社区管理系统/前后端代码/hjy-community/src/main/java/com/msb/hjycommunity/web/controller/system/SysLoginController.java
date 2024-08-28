package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.utils.ChainedMap;
import com.msb.hjycommunity.common.utils.ServletUtils;
import com.msb.hjycommunity.framework.service.SysPermissionService;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.domain.vo.LoginBody;
import com.msb.hjycommunity.system.domain.vo.RouterVo;
import com.msb.hjycommunity.system.service.SysLoginService;
import com.msb.hjycommunity.system.service.SysMenuService;
import com.msb.hjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 * @author spikeCong
 * @date 2023/5/5
 **/
@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录方法
     * @param loginBody
     * @return: com.msb.hjycommunity.common.utils.ChainedMap
     */
    @PostMapping("/login")
    public ChainedMap login(@RequestBody LoginBody loginBody){
        //生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword()
                , loginBody.getCode(), loginBody.getUuid());

        return ChainedMap.create().set("token",token);
    }

    /**
     * 获取用户信息及其权限信息
     * @param
     * @return: com.msb.hjycommunity.common.utils.ChainedMap
     */
    @GetMapping("/getInfo")
    public ChainedMap getInfo(){

        //用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        //角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        //权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");
        map.put("user",user);
        map.put("roles",roles);
        map.put("permissions",permissions);
        return map;
    }


    /**
     * 获取路由信息
     * @param
     * @return: 路由信息
     */
    @GetMapping("/getRouters")
    public BaseResponse getRouters(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        //获取菜单列表
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(user.getUserId());
        //将获取到的菜单列表转换为 前端需要的路由列表
        List<RouterVo> routerVoList = sysMenuService.buildMenus(menus);

        return BaseResponse.success(routerVoList);
    }
}
