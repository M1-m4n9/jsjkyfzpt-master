package com.sicnu.person.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("p_role_power")
public class RolePowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 中间表id
	 */
	@TableId
	private Integer id;
	/**
	 * 角色id
	 */
	private Integer rid;
	/**
	 * 权限id
	 */
	private Integer pid;

}
