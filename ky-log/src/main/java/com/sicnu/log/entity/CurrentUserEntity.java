package com.sicnu.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author LiuChuang
 * @Date 2022/11/15 10:45
 * @PackageName:com.sicnu.log.entity
 * @ClassName: UserEntity
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class CurrentUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 性别(0-女，1-男)
	 */
	private Integer sex;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像地址
	 */
	private String headUrl;
}