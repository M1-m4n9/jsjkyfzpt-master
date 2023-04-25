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
@TableName("c_major_type")
public class MajorTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 专业类型id
	 */
	@TableId
	private Integer id;
	/**
	 * 专业类型名称
	 */
	private String name;
	/**
	 * 专业类型介绍
	 */
	private String introduction;

}
