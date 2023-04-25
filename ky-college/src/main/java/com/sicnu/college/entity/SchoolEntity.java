package com.sicnu.college.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@Data
@TableName("c_school")
public class SchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 院校id
	 */
	@TableId
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
	private Integer province;
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
	private Integer collegeType;
	/**
	 * 院校属性
	 */
	private Integer collegeAttribute;
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
