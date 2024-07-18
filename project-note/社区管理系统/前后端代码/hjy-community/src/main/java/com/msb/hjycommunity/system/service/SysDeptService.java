package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.system.domain.SysDept;

import java.util.List;

/**
 * @author spikeCong
 * @date 2023/3/30
 **/
public interface SysDeptService {

    /**
     * 查询部门管理数据
     * @param sysDept
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysDept>
     */
    public List<SysDept> selectDeptList(SysDept sysDept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);

    /**
     * 是否存在部门子节点
     * @param deptId
     * @return: boolean
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);


    /**
     * 构建前端需要的下拉树结构
     * @param deptList 部门列表
     * @return: 下拉树结构表
     */
    List<TreeSelect> buildDeptTreeSelect(List<SysDept> deptList);
}
