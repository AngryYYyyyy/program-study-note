package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.ChainedMap;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysRole;
import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.service.SysPostService;
import com.msb.hjycommunity.system.service.SysRoleService;
import com.msb.hjycommunity.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author spikeCong
 * @date 2023/5/25
 **/
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPostService postService;

    /**
     * 获取用户分页列表(条件查询)
     */
    @GetMapping("/list")
    public PageResult list(SysUser user){
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getData(list);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public BaseResponse remove(@PathVariable Long[] userIds){
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public BaseResponse restPwd(@RequestBody SysUser user){
        //校验用户是否是admin
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public BaseResponse changeStatus(@RequestBody SysUser user){
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取详细新
     *      新增: 不需要携带用户编号
     */
    @GetMapping(value = {"/" , "/{userId}"})
    public ChainedMap getInfo(@PathVariable(value = "userId",required = false) Long userId){

        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");

        //获取角色列表
        List<SysRole> roleList = roleService.selectRoleAll();

        //封装用户关联角色信息
        if(SysUser.isAdmin(userId)){
           map.put("roles",roleList);
        }else{
            List<SysRole> cList = roleList.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
            map.put("roles",cList);
        }
        //用户管理部门
        map.put("posts",postService.selectPostAll());

        //如果是修改操作
        if(!Objects.isNull(userId)){
            map.put("data",userService.selectUserById(userId));
            map.put("postIds",postService.selectPostListByUserId(userId));
            map.put("roleIds",roleService.selectRoleListByUserId(userId));
        }
        return map;
    }

    /**
     * 新增用户
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysUser user){

        if(UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))){
            return BaseResponse.fail("新增用户 '" + user.getUserName() + "'失败,登录账号已经存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))){
            return BaseResponse.fail("新增用户 '" + user.getUserName() + "'失败,手机号码已经存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))){
            return BaseResponse.fail("新增用户 '" + user.getUserName() + "'失败,邮箱账号已经存在");
        }
        user.setCreateBy(SecurityUtils.getUserName());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysUser user){

        //校验用户是否允许操作
        userService.checkUserAllowed(user);

        if(UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))){
            return BaseResponse.fail("修改用户 '" + user.getUserName() + "'失败,手机号码已经存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))){
            return BaseResponse.fail("修改用户 '" + user.getUserName() + "'失败,邮箱账号已经存在");
        }

        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(userService.updateUser(user));
    }
}
