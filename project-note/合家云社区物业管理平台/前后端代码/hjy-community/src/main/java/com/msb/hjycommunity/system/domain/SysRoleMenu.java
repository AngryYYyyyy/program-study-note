package com.msb.hjycommunity.system.domain;

/**
 * 角色和菜单关联 sys_role_menu
 * @author spikeCong
 * @date 2023/5/26
 **/
public class SysRoleMenu {

    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
