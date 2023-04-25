package com.sicnu.study.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 16:03
 * @PackageName:com.sicnu.person.entity
 * @ClassName: UserCollectionsEntity
 * @Description: TODO
 * @Version 1.0
 */
@Data
@TableName("s_collection_user")
public class CollectionUserEntity
{
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer uid;
	
	/**
	 * 资源合集id
	 */
	private Integer collectionId;
	
	
	/**
	 * 是否点赞  1是  0否
	 */
	private Integer liked;
	
	/**
	 * 是否收藏  1是  0否
	 */
	private Integer collected;
	
	/**
	 * 类型 1视频 0文本 2评论
	 */
	private Integer collectionType;
	
	
	
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
}
