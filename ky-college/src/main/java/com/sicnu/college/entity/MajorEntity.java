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
@TableName("c_major")
public class MajorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 专业id
	 */
	@TableId
	private Integer id;
	/**
	 * 专业名称
	 */
	private String name;
	/**
	 * 专业代码
	 */
	private String majorCode;
	/**
	 * 专业简介
	 */
	private String introduction;
	/**
	 * 学校id
	 */
	private Integer sid;
	/**
	 * 专业类型id
	 */
	private Integer mtid;
	/**
	 * 院的id
	 */
	private Integer did;

}
