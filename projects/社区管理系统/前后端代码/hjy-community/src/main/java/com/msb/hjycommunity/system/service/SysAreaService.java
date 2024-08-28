package com.msb.hjycommunity.system.service;

import com.msb.hjycommunity.system.domain.dto.SysAreaDto;

import java.util.List;

/**
 * @author spikeCong
 * @date 2023/3/7
 **/
public interface SysAreaService {


    /**
     * 获取区域数据的完整树
     * @param
     * @return: java.util.List<com.msb.hjycommunity.system.domain.dto.SysAreaDto>
     */
    List<SysAreaDto> findAreaAsTree();

}
