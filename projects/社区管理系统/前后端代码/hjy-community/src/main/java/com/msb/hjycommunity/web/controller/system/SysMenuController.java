package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.common.utils.ChainedMap;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.common.utils.ServletUtils;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.domain.SysMenu;
import com.msb.hjycommunity.system.service.SysMenuService;
import com.msb.hjycommunity.system.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单信息管理
 * @author spikeCong
 * @date 2023/5/26
 **/
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private TokenService tokenService;


    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public BaseResponse list(SysMenu menu){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
        return BaseResponse.success(menuList);
    }

    /**
     * 获取菜单详情
     */
    @GetMapping(value = "/{menuId}")
    public BaseResponse getInfo(@PathVariable Long menuId){
        return BaseResponse.success(menuService.selectMenuById(menuId));
    }

    /**
     * 新增菜单
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysMenu menu){

        if(UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))){
            return BaseResponse.fail("新增菜单" + menu.getMenuName() + "失败,菜单已经存在");
        } else if(UserConstants.YES_FRAME.equals(menu.getIsFrame()) &&
                !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP,Constants.HTTPS)){
            return  BaseResponse.fail("新增菜单" + menu.getMenuName() + "失败,地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUserName());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysMenu menu){
        if(UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))){
            return BaseResponse.fail("新增菜单" + menu.getMenuName() + "失败,菜单已经存在");
        } else if(UserConstants.YES_FRAME.equals(menu.getIsFrame()) &&
                !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP,Constants.HTTPS)){
            return  BaseResponse.fail("新增菜单" + menu.getMenuName() + "失败,地址必须以http(s)://开头");
        } else if(menu.getMenuId().equals(menu.getParentId())){
            return  BaseResponse.fail("新增菜单" + menu.getMenuName() + "失败,上级菜单不能选择自己");
        }

        menu.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public BaseResponse remove(@PathVariable("menuId") Long menuId){
        if(menuService.hasChildByMenuId(menuId)){
            return BaseResponse.fail("存在子菜单,不允许删除");
        }
        if(menuService.checkMenuExistRole(menuId)){
            return BaseResponse.fail("菜单已分配,不能删除");
        }

        return toAjax(menuService.deleteMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public BaseResponse treeSelect(SysMenu menu){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<SysMenu> sysMenus = menuService.selectMenuList(menu, userId);

        List<TreeSelect> treeSelects = menuService.buildMenuTreeSelect(sysMenus);
        return BaseResponse.success(treeSelects);
    }

    /**
     * 加载对应角色的菜单列表
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ChainedMap roleMenuTreeSelect(@PathVariable Long roleId){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menuList = menuService.selectMenuList(loginUser.getUser().getUserId());

        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");

        //封装当前角色拥有的菜单权限id
        map.put("checkedKeys",menuService.selectMenuListByRoleId(roleId));
        //菜单树
        map.put("menus",menuService.buildMenuTreeSelect(menuList));

        return map;
    }

}
