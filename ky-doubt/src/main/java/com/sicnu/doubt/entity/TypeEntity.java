package com.sicnu.doubt.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
@Data
@TableName("d_type")
public class TypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 类型id
	 */
	@TableId
	private Integer id;
	/**
	 * 类型名称
	 */
	@NotEmpty(message = "类型名称未指定",groups = {Add.class, Update.class})
	private String name;
	/**
	 * 类型介绍
	 */
	@NotNull(message = "介绍不为NUll",groups = {Add.class,Update.class})
	private String introduction;

}
