package com.sicnu.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@Data
public class UserEntity implements Serializable, UserDetails {
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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	 * 个人简介
	 */
	private String introduction;
	/**
	 * 是否冻结
	 */
	private Integer status;
	/**
	 * 粉丝数量
	 */
	private Integer fansCount;
	/**
	 * 关注数量
	 */
	private Integer followCount;
	/**
	 * 访问量
	 */
	private Integer visitCount;
	/**
	 * 专业名称
	 */
	private String majorName;
	/**
	 * 学校名字
	 */
	private String schoolName;
	/**
	 * 0-学生,1-老师，-1管理员
	 */
	private Integer type;
	/**
	 * 是否认证(0-没有 1-有)
	 */
	private Integer isAuth;
	/**
	 * 认证材料地址
	 */
	private String certificationMaterialsUrl;
	/**
	 * 头像地址
	 */
	private String headUrl;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;

    /**
	 * 角色
	 */
	private List<RoleEntity>  roles;


	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
