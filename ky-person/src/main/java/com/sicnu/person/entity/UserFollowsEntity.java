package com.sicnu.person.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 13:03
 * @PackageName:com.sicnu.person.entity
 * @ClassName: UserFollowsEntity
 * @Description: 用户关注表
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("p_user_follows")
public class UserFollowsEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer uid;
	
	/**
	 * 关注id
	 */
	private Integer followedUid;
	
	/**
	 * 是否互相关注  1互相关注  0单向关注
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
}
