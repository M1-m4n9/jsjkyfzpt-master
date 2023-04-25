package com.sicnu.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

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
public class RoleEntity implements Serializable, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
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


	@JsonIgnore
	@Override
	public String getAuthority() {
		return name;
	}

	public RoleEntity(String name) {
		this.name = name;
	}
}
