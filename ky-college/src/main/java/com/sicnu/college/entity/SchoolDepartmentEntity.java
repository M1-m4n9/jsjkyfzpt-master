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
@TableName("c_school_department")
public class SchoolDepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 学校和学院关系表的id
	 */
	@TableId
	private Integer id;
	/**
	 * 学校id
	 */
	private Integer sid;
	/**
	 * 学院id
	 */
	private Integer did;

}
