package com.sicnu.person.feign.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@Data
public class CourseCollectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 视频课程或者文件合集id
	 */

	private Integer id;
	/**
	 * 视频合集名称
	 */
	private String name;
	/**
	 * 封面url
	 */
	private String coverUrl;
	/**
	 * 视频合集介绍
	 */
	private String introduction;
	/**
	 * 类型(区分是视频,还是纸质)
	 */
	private Integer type;
	/**
	 * 分类
	 */
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
	private Integer uid;
	/**
	 * 发布时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 得分(计算热度排行)
	 */
	private Double score;
	/**
	 * 是否删除(用来逻辑删除) 0-未删除 1-已删除 默认1
	 */
	private Integer isDeleted;

}
