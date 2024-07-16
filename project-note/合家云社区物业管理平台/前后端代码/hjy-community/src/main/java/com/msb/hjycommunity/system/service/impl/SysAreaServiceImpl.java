package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.system.domain.SysArea;
import com.msb.hjycommunity.system.domain.dto.SysAreaDto;
import com.msb.hjycommunity.system.mapper.SysAreaMapper;
import com.msb.hjycommunity.system.service.SysAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author spikeCong
 * @date 2023/3/7
 **/
@Service
public class SysAreaServiceImpl implements SysAreaService {

    @Resource
    private SysAreaMapper sysAreaMapper;


    @Override
    public List<SysAreaDto> findAreaAsTree() {

        //获取区域表数据
        List<SysArea> list = sysAreaMapper.findAll();

        return list.stream()    //把集合转换为流
                .filter(area -> area.getParentCode().equals(0))  //筛选pid为0的area根节点对象
                .map(area -> { //将area进行转换
                    SysAreaDto sysAreaDto = new SysAreaDto();
                    sysAreaDto.setCode(area.getCode());
                    sysAreaDto.setName(area.getName());
                    sysAreaDto.setChildren(getChildrenArea(sysAreaDto,list));
                    return sysAreaDto;
                }).collect(Collectors.toList());
    }

    /**
     * 递归设置区域信息
     * @param sysAreaDto 上级区域信息
     * @param list  所有区域信息
     * @return: java.util.List<com.msb.hjycommunity.system.domain.dto.SysAreaDto>
     */
    private List<SysAreaDto> getChildrenArea(SysAreaDto sysAreaDto, List<SysArea> list) {

        List<SysArea> subAreaList = list.stream().filter(area -> area.getParentCode().equals(sysAreaDto.getCode())) //获取当前父区域的子节点
                .collect(Collectors.toList());//把当前流转换为一个List集合

        if(subAreaList != null && subAreaList.size() != 0){
            return subAreaList.stream().map(area -> {
                SysAreaDto subAreaDto = new SysAreaDto();
                subAreaDto.setName(area.getName());
                subAreaDto.setCode(area.getCode());
                //设置子节点,递归调用直到获取到叶子结点为止
                subAreaDto.setChildren(getChildrenArea(subAreaDto,list));

                return subAreaDto;
            }).collect(Collectors.toList());
        }

        return null;
    }
}
