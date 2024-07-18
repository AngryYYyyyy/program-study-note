package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.domain.SysRole;
import com.msb.hjycommunity.system.domain.SysRoleMenu;
import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.domain.vo.MetaVo;
import com.msb.hjycommunity.system.domain.vo.RouterVo;
import com.msb.hjycommunity.system.mapper.SysMenuMapper;
import com.msb.hjycommunity.system.mapper.SysRoleMapper;
import com.msb.hjycommunity.system.mapper.SysRoleMenuMapper;
import com.msb.hjycommunity.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author spikeCong
 * @date 2023/5/10
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public Set<String> selectMenuPermissionByUserId(Long userId) {

        List<String> list = menuMapper.selectMenuPermissionByUserId(userId);
        HashSet<String> permsSet = new HashSet<>();

        for (String perms : list) {
            if(!StringUtils.isEmpty(perms)){
                permsSet.add(perms);
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {

        List<SysMenu> menus = null;

        if(userId != null && userId == 1L){
            menus = menuMapper.selectMenuTreeAll();
        }else{
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }

        //todo 封装子菜单
        return getChildPerms(menus,0);
    }


    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {

        List<RouterVo> routers = new ArrayList<>();

        for (SysMenu menu : menus) {
            RouterVo routerVo = new RouterVo();
            routerVo.setName(getRouterName(menu));
            routerVo.setPath(getRouterPath(menu));
            routerVo.setComponent(getComponent(menu));
            routerVo.setHidden("1".equals(menu.getVisible()));
            routerVo.setMeta(new MetaVo(menu.getMenuName(),menu.getIcon(),"1".equals(menu.getIsCache())));
            List<SysMenu> subMenuList = menu.getChildren();
            if(!subMenuList.isEmpty() && subMenuList.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
                routerVo.setAlwaysShow(true);
                routerVo.setRedirect("noRedirect");
                routerVo.setChildren(buildMenus(subMenuList)); //递归设置子菜单
            }
            routers.add(routerVo);
        }
        return routers;
    }


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> menuList = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String menu : menuList) {
            if(!StringUtils.isEmpty(menu)){
                permsSet.add(menu);
            }
        }
        return permsSet;
    }


    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Long userId) {

        return selectMenuList(new SysMenu(), userId);
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }


    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(SysMenu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(SysMenu menu) {
        Long menuId = Objects.isNull(menu.getMenuId()) ? -1L : menu.getMenuId();
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (!Objects.isNull(info) && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取组件信息
     * @param menu
     * @return: 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if(!StringUtils.isEmpty(menu.getComponent())){
            component = menu.getComponent();
        }else if(menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
            component = UserConstants.PARENT_VIEW;
        }

        return component;
    }

    /**
     * 获取路由地址
     * @param menu 菜单信息
     * @return: 路由地址
     */
    public String getRoutePath(SysMenu menu) {
        String routerPath = menu.getPath();
        //非外链 并且是一级目录,菜单类型为 M(目录)
        if(0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())){
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

    /**
     * 获取路由名称
     * @param menu  菜单信息
     * @return: 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = org.apache.commons.lang3.StringUtils.capitalize(menu.getPath());
        return routerName;
    }


    /**
     * 获取路由地址
     * @param menu
     * @return: java.lang.String
     */
    private String getRouterPath(SysMenu menu) {
        String path = menu.getPath();
         if(menu.getParentId().intValue() == 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())){
             path = "/" + menu.getPath();
         }

         return path;
    }

    /**
     * 获取路由名称
     * @param menu
     * @return: java.lang.String
     */
    private String getRouterName(SysMenu menu) {

        String routerPath = menu.getPath();
        String routerName = org.apache.commons.lang3.StringUtils.capitalize(routerPath);
        return routerName;
    }

    /**
     * 根据父节点ID 获取子节点
     * @param menus 菜单列表
     * @param parentId 父菜单Id
     * @return: 有父子结构的菜单集合
     */
    public List<SysMenu> getChildPerms(List<SysMenu> menus, int parentId) {

        List<SysMenu> returnList = new ArrayList<>();
        menus.stream()
                .filter(m -> m.getParentId() == parentId)
                .forEach(m -> {
                    recursionFn(menus,m);
                    returnList.add(m);
                });

        return returnList;
    }

    /**
     * 递归获取子菜单
     * @param menus
     * @param m
     */
    private void recursionFn(List<SysMenu> menus, SysMenu m) {
        //1.获取子节点列表
        List<SysMenu> childMenu = getChildList(menus,m);
        m.setChildren(childMenu);
        for (SysMenu subMenu : childMenu) {
            //判断  子节点下面还有子节点
            if(getChildList(menus,subMenu).size() > 0 ? true : false){
                recursionFn(menus,subMenu);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param menus
     * @param m
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysMenu>
     */
    private List<SysMenu>  getChildList(List<SysMenu> menus, SysMenu m) {
        return menus.stream()
                .filter(sub -> sub.getParentId().longValue() == m.getMenuId().longValue())
                .collect(Collectors.toList());
    }


    /**
     * 构建前端所需要的下拉树结构
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建树结构
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {

        List<SysMenu> returnList = new ArrayList<>();
        //获取所有菜单id
        List<Long> menuIds = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());

        menus.forEach(menu -> {
            //如果是顶级节点,遍历该父节点下的所有子节点
            if(!menuIds.contains(menu.getParentId())){
                recursionFn(menus,menu);
                returnList.add(menu);
            }
        });

        return returnList.isEmpty() ? menus : returnList;
    }
}
