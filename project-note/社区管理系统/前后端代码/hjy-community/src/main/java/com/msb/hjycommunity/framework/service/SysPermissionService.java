package com.msb.hjycommunity.framework.service;

import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.service.SysMenuService;
import com.msb.hjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 * @author spikeCong
 * @date 2023/5/10
 **/
@Component
public class SysPermissionService {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取角色数据权限
     * @param user
     * @return: java.util.Set<java.lang.String>
     */
    public Set<String> getRolePermission(SysUser user){
        Set<String> roles = new HashSet<>();
        //是否有管理员权限
        if(user.isAdmin()){
            roles.add("admin");
        }else{
            roles = roleService.selectRolePermissionByUserId(user.getUserId());
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * @param user
     * @return: java.util.Set<java.lang.String>
     */
    public Set<String> getMenuPermission(SysUser user){
        Set<String> perms = new HashSet<>();
        //是否是管理员权限,如果是拥有所有权限
        if(user.isAdmin()){
            perms.add("*:*:*");
        }else{
            perms = sysMenuService.selectMenuPermissionByUserId(user.getUserId());
        }
        return perms;
    }
}
