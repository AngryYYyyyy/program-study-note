package com.msb.hjycommunity.community.service;

import com.msb.hjycommunity.community.domain.HjyCommunity;
import com.msb.hjycommunity.community.domain.dto.HjyCommunityDto;
import com.msb.hjycommunity.community.domain.vo.HjyCommunityVo;

import java.util.List;

/**
 * @author spikeCong
 * @date 2023/3/6
 **/
public interface HjyCommunityService {

    /**
     * 根据条件查询小区信息列表
     * @param hjyCommunity
     * @return: java.util.List<com.msb.hjycommunity.community.domain.dto.HjyCommunityDto>
     */
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);

    /**
     * 新增小区信息
     * @param hjyCommunity
     * @return: int
     */
    int insertHjyCommunity(HjyCommunity hjyCommunity);

    /**
     * 根据Id获取小区详情
     * @param communityId
     * @return: com.msb.hjycommunity.community.domain.HjyCommunity
     */
    HjyCommunity selectHjyCommunityById(Long communityId);


    /**
     * 修改小区
     * @param hjyCommunity
     * @return: int
     */
    int updateHjyCommunity(HjyCommunity hjyCommunity);


    /**
     * 删除操作
     * @param communityIds
     * @return: int
     */
    int deleteHjyCommunity(Long[] communityIds);


    /**
     * 获取小区下拉列表
     * @param hjyCommunity
     * @return: java.util.List<com.msb.hjycommunity.community.domain.vo.HjyCommunityVo>
     */
    List<HjyCommunityVo> queryPullDown(HjyCommunity hjyCommunity);

}
