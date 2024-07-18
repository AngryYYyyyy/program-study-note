package com.msb.hjycommunity.common.core.domain;

import com.msb.hjycommunity.system.domain.SysDept;
import com.msb.hjycommunity.system.domain.SysMenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 构建树形结构实体类
 * @author spikeCong
 * @date 2023/5/24
 **/
public class TreeSelect implements Serializable {

    //节点id
    private Long id;

    //节点名称
    private String label;

    //子节点
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    //构造方法 接收部门对象,构建树形结构TreeSelect对象
    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    //构造方法 接收菜单对象,构建TreeSelect
    public TreeSelect(SysMenu menu){
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
