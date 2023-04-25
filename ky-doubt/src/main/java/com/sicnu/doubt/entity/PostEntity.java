package com.sicnu.doubt.entity;

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
@TableName("d_post")
public class PostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 帖子id
	 */
	@TableId
	@Null(message = "新增帖子不能指定id",groups = {Add.class})
	private Integer id;
	/**
	 * 帖子用户
	 */
	@NotEmpty(message = "帖子用户id不能为空",groups = {Add.class, Update.class})
	private Integer uid;
	/**
	 * 帖子标题
	 */
	@NotEmpty(message = "帖子标题不能为空",groups = {Add.class, Update.class})
	private String title;
	/**
	 * 摘要
	 */
	@NotEmpty(message = "帖子的摘要不能为空",groups = {Add.class, Update.class})
	private String digest;
	/**
	 * 帖子内容
	 */
	@NotEmpty(message = "帖子内容不能为空",groups = {Add.class,Update.class})
	private String content;
	/**
	 * 审核是否通过(0-不通过 ，1 - 通过)
	 */
	@NotNull(message = "审核可以为空但不能为NUll",groups = {Add.class, Update.class})
	private Integer status;
	/**
	 * 帖子类型
	 */
	@NotEmpty(message = "类型不能为空",groups = {Add.class, Update.class})
	private Integer tid;
	/**
	 * 是否删除(0-删除了 1 - 没有删除)
	 */
	@NotEmpty(message = "评论删除与否不确定",groups = {Update.class})
	private Integer isDeleted;
	/**
	 * 评论量
	 */
	private Integer commentCount;
	/**
	 * 点赞量
	 */
	private Integer likeCount;
	/**
	 * 浏览量
	 */
	private Integer visitCount;
	/**
	 * 收藏量
	 */
	private Integer postCount;
	/**
	 * 得分
	 */
	private Double score;
	/**
	 * 发布时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 帖子封面图
	 */
	private String url;

	@TableField(exist = false)
	private Integer isLike;
	@TableField(exist = false)
	private Integer isPost;
}
