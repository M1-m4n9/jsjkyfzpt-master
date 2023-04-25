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
@TableName("c_department")
public class DepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 院的id
	 */
	@TableId
	private Integer id;
	/**
	 * 院的名字
	 */
	private String name;
	/**
	 * 院的简介
	 */
	private String introduction;

}
