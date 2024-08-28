package com.msb.hjycommunity.community.domain.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出Excel 实体类
 * @author spikeCong
 * @date 2023/4/2
 **/
@ExcelTarget("community")
public class HjyCommunityExcelDto implements Serializable {

    /** 小区ID */
    @Excel(name = "序号")
    private Long communityId;

    /** 小区名称 */
    @Excel(name = "小区名称")
    private String communityName;

    /** 小区编码 */
    @Excel(name = "小区编码")
    private String communityCode;

    //省名称
    @Excel(name = "省")
    private String communityProvinceName;

    //市名称
    @Excel(name = "市")
    private String communityCityName;

    //区名称
    @Excel(name = "区/县")
    private String communityTownName;

    @Excel(name = "创建时间",exportFormat = "yyyy年MM月dd日")
    private Date createTime;

    @Excel(name = "备注")
    private String remark;

    public HjyCommunityExcelDto() {
    }

    public HjyCommunityExcelDto(Long communityId, String communityName, String communityCode, String communityProvinceName, String communityCityName, String communityTownName, Date createTime, String remark) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.communityCode = communityCode;
        this.communityProvinceName = communityProvinceName;
        this.communityCityName = communityCityName;
        this.communityTownName = communityTownName;
        this.createTime = createTime;
        this.remark = remark;
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

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityProvinceName() {
        return communityProvinceName;
    }

    public void setCommunityProvinceName(String communityProvinceName) {
        this.communityProvinceName = communityProvinceName;
    }

    public String getCommunityCityName() {
        return communityCityName;
    }

    public void setCommunityCityName(String communityCityName) {
        this.communityCityName = communityCityName;
    }

    public String getCommunityTownName() {
        return communityTownName;
    }

    public void setCommunityTownName(String communityTownName) {
        this.communityTownName = communityTownName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
