package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务层
 * @author spikeCong
 * @date 2023/5/10
 **/
public interface SysMenuService {

    /**
     * 根据用户ID 查询菜单信息
     * @param userId
     * @return: java.util.Set<java.lang.String>
     */
    public Set<String> selectMenuPermissionByUserId(Long userId);


    /**
     * 根据用户Id 查询菜单信息
     * @param userId
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysMenu>
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由需要的菜单
     * @param menus
     * @return: java.util.List<com.msb.hjycommunity.system.domain.vo.RouterVo>
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);


    /**
     * 根据用户Id查询用户权限
     * @param userId
     * @return: java.util.Set<java.lang.String>
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(Long userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Integer> selectMenuListByRoleId(Long roleId);



    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkMenuExistRole(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(SysMenu menu);
}
