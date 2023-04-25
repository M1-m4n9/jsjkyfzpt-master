package com.sicnu.person.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("p_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId
	private Integer id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色介绍
	 */
	private String introduction;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 创建这个角色的管理员的id
	 */
	private Integer uid;

	/**
	 * 这个角色对应的路径有那些
	 */
	@TableField(exist = false)
	private List<PowerEntity> paths;

	/**
	 * 前端传过来的value数组
	 */
	@TableField(exist = false)
	private List<List<Integer>> value;
}
