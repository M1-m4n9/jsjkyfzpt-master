package com.sicnu.study.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;


/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@Data
@TableName("s_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 评论id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id(是那个用户在评论)
	 */
	@NotEmpty(message = "用户id不能为空",groups = {Add.class, Update.class})
	private Integer uid;
	/**
	 * 评论类型(区分是评论集合,还是评论别人的评论)
	 */
	@NotEmpty(message = "评论类型不能为空",groups = {Add.class, Update.class})
	private Integer entityType;
	/**
	 * 评论对象id(集合/别人评论)
	 */
	@NotEmpty(message = "评论对象id不能为空",groups = {Add.class,Update.class})
	private Integer entityId;
	/**
	 * 评论是那个人的评论(楼主)
	 */
	private Integer targetId;
	/**
	 * 点赞量
	 */
	@NotEmpty(message = "点赞量不能为空",groups = {Add.class,Update.class})
	private Integer likeCount;
	/**
	 * 评论内容
	 */
	@NotEmpty(message = "评论不能为空",groups = {Add.class})
	private String content;
	/**
	 * 是否置顶(0-不置顶 1-置顶)
	 */
	@NotEmpty(message = "评论制定修改不能为空",groups = {Update.class})
	private Integer type;
	/**
	 * 是否删除
	 */
	@NotEmpty(message = "评论删除与否不确定",groups = {Update.class})
	private Integer isDeleted;
	/**
	 * 评论时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 置顶时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date topTime;

	@TableField(exist = false)
	private Integer isLike;
	
}
