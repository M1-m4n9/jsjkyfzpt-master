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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@Data
@TableName("s_course_collection")
public class CourseCollectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 视频课程或者文件合集id
	 */
	@TableId
	@NotNull(message = "修改必须指定合集id", groups = {Update.class})
	@Null(message = "新增不能指定id", groups = {Add.class})
	private Integer id;
	/**
	 * 视频合集名称
	 */
	@NotEmpty(message = "必须指定标题",groups = {Add.class, Update.class})
	private String name;
	/**
	 * 封面url
	 */
	@NotEmpty(message = "封面不能为空",groups = {Add.class, Update.class})
	private String coverUrl;
	/**
	 * 视频合集介绍
	 */
	@NotEmpty(message = "简介不能为空",groups = {Add.class, Update.class})
	private String introduction;
	/**
	 * 类型(区分是视频,还是纸质)
	 */
	@NotEmpty(message = "类型必须选择 1视频 0纸质", groups = {Add.class, Update.class})
	private Integer type;
	/**
	 * 分类
	 */
	@NotEmpty(message = "分类 视频合集或纸质合集", groups = {Add.class, Update.class})
//	private String classification;
	private Integer collectionClassificationId;
	/**
	 * 点赞总数
	 */
	private Integer likeCount;
	/**
	 * 播放量(视频)/下载量(纸质)
	 */
	private Integer playCount;
	/**
	 * 分享量
	 */
	private Integer shareCount;
	/**
	 * 收藏量
	 */
	private Integer collectionCount;
	/**
	 * 评论量
	 */
	private Integer commentCount;
	/**
	 * 课程时长
	 */
	@JsonFormat(pattern = "HH:ss:mm")
	private Date courseDuration;
	/**
	 * 发布者id
	 */
	@NotEmpty(message = "发布者id不能为空",groups = {Add.class, Update.class})
	private Integer uid;
	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 得分(计算热度排行)
	 */
	@NotEmpty(message = "得分不能为空",groups = {Add.class, Update.class})
	private Double score;
	/**
	 * 是否删除(用来逻辑删除) 0-未删除 1-已删除 默认1
	 */
	@NotEmpty
	private Integer isDeleted;

	@TableField(exist = false)
	private Integer isLike;

	@TableField(exist = false)
	private Integer isCollection;

}
