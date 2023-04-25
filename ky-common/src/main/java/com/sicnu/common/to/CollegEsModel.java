package com.sicnu.common.to;

import lombok.Data;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/25 16:59
 */
@Data
public class CollegEsModel {

    /**
     * 院校id
     */
    private Integer id;
    /**
     * 院校名称
     */
    private String name;
    /**
     * 院校简介
     */
    private String introduction;
    /**
     * 省份
     */
    private String province;
    /**
     * 封面url
     */
    private String coverUrl;
    /**
     * 官网地址
     */
    private String officialWebsite;
    /**
     * 院校类型(综合类/理工类)
     */
    private String collegeType;
    /**
     * 院校属性
     */
    private String collegeAttribute;
    /**
     * 访问量
     */
    private Integer visitCount;
    /**
     * 研究生官网
     */
    private String postgraduateWebsite;
    /**
     * 院校公告
     */
    private String collegeAnnouncement;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 通讯地址
     */
    private String postalAddress;
}
