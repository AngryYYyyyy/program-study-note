package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.domain.TreeSelect;
import com.msb.hjycommunity.common.core.exception.CustomException;
import com.msb.hjycommunity.system.domain.SysDept;
import com.msb.hjycommunity.system.mapper.SysDeptMapper;
import com.msb.hjycommunity.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author spikeCong
 * @date 2023/3/30
 **/
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     * @param sysDept
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysDept>
     */
    @Override
    public List<SysDept> selectDeptList(SysDept sysDept) {

        return deptMapper.selectDeptList(sysDept);
    }

    /**
     * 根据id 查询部门信息
     * @param deptId
     * @return: com.msb.hjycommunity.system.domain.SysDept
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 新增部门信息
     * @param dept
     * @return: int
     */
    @Override
    public int insertDept(SysDept dept) {
        //1. 获取当前节点的父节点
        SysDept parentDept = deptMapper.selectDeptById(dept.getParentId());

        //2. 判断父节点是不是正常状态,非正常状态 不允许插入
        if(!UserConstants.DEPT_NORMAL.equals(parentDept.getStatus())){
            throw new CustomException(500,"部门停用,不予许新增");
        }

        //3.设置 组级列表字段的值
        dept.setAncestors(parentDept.getAncestors() +"," +dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改部门信息
     * @param dept
     * @return: int
     */
    @Override
    public int updateDept(SysDept dept) {

        //获取新的父节点
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());

        //获取旧节点数据
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());

        if(!Objects.isNull(newParentDept) && !Objects.isNull(oldDept)){
            //设置最新的组级列表
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            dept.setAncestors(newAncestors);

            //修改子元素的关系
            String oldAncestors = oldDept.getAncestors();
            updateDeptChildren(dept.getDeptId(),newAncestors,oldAncestors);
        }

        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素的关系
     * @param deptId    被修改的部门
     * @param newAncestors  新的父id集合
     * @param oldAncestors  旧的父id集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> childrenDept = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : childrenDept) {
            child.setAncestors(child.getAncestors().replace(oldAncestors,newAncestors));
        }

        if(childrenDept.size() > 0){
            deptMapper.updateDeptChildren(childrenDept);
        }
    }

    /**
     * 删除部门
     * @param deptId
     * @return: int
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     * @param dept
     * @return: java.lang.String
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        SysDept sysDept = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if(!Objects.isNull(sysDept)){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 是否存在子节点
     * @param deptId
     * @return: boolean
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int row = deptMapper.hasChildByDeptId(deptId);
        return row > 0 ? true : false;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int row = deptMapper.checkDeptExistUser(deptId);
        return row > 0 ? true : false;    }


    /**
     * 构建前端需要的下拉树结构
     * @param deptList 部门列表
     * @return: 下拉树结构表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> deptList) {
        List<SysDept> deptTrees = buildDeptTree(deptList);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建部门树结构
     * @param deptList
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysDept>
     */
    private List<SysDept> buildDeptTree(List<SysDept> deptList) {

        //要返回的list
        List<SysDept> returnList = new ArrayList<>();

        //获取所有部门的deptId
        List<Long> deptIds = new ArrayList<>();
        for (SysDept sysDept : deptList) {
            deptIds.add(sysDept.getDeptId());
        }

        deptList.stream()
                .filter(dept -> !deptIds.contains(dept.getParentId()))
                .forEach(dept ->{
                    //递归获取子节点
                    recursionFn(deptList,dept);
                    returnList.add(dept);
                });

        return returnList;
    }

    /**
     * 递归操作
     * @param deptList 部门集合
     * @param dept 父部门
     */
    private void recursionFn(List<SysDept> deptList, SysDept dept) {
        //获取子节点
        List<SysDept> childList = getChildList(deptList,dept);
        dept.setChildren(childList);
        for (SysDept child : childList) {
            //判断是否还有子节点
            if(hasChild(deptList,child)){
                //递归调用
                recursionFn(deptList,child);
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> childList, SysDept child) {
        return getChildList(childList,child).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     * @param deptList
     * @param dept
     * @return: java.util.List<com.msb.hjycommunity.system.domain.SysDept>
     */
    private List<SysDept> getChildList(List<SysDept> deptList, SysDept dept) {
        List<SysDept> subList = new ArrayList<>();
        subList = deptList.stream()
                .filter(subDept -> !Objects.isNull(subDept.getParentId())
                    && subDept.getParentId().longValue() == dept.getDeptId().longValue())
                .collect(Collectors.toList());

        return subList;
    }

}
