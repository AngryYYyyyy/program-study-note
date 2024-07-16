package com.msb.hjycommunity.community.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msb.hjycommunity.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 数据传输对象
 * @author spikeCong
 * @date 2023/3/3
 **/
public class HjyCommunityVo implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long communityId;

    /** 小区名称 */
    private String communityName;

    @Override
    public String toString() {
        return "HjyCommunityVo{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                '}';
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
