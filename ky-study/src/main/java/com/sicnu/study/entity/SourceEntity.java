package com.sicnu.study.entity;

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
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@Data
@TableName("s_source")
public class SourceEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 资源id
	 */
	@TableId
	@NotNull(message = "修改必须指定资源id", groups = {Update.class})
	@Null(message = "新增不能指定id", groups = {Add.class})
	private Integer id;
	/**
	 * 名称
	 */
	@NotEmpty(message = "资源名称不能为空", groups = {Add.class, Update.class})
	private String name;
	/**
	 * 类型(区分是视频还是纸质)  1视频 0纸质
	 */
	@NotEmpty(message = "类型必须选择 1视频 0纸质", groups = {Add.class, Update.class})
	private Integer type;
	/**
	 * 视频地址/纸质资料地址
	 */
	@NotEmpty(message = "资源地址不能为空", groups = {Add.class, Update.class})
	private String videoUrl;
	/**
	 * 视频时长	视频类型才有，应该自动获得，可为空
	 */
	@JsonFormat(pattern = "HH:mm:ss")
	private Date duration;
	/**
	 * 排序(集数排序)
	 */
	@NotEmpty(message = "合集中的排序不能为空", groups = {Add.class, Update.class})
	private Integer sort;
	/**
	 * 是否删除(用来逻辑删除) 0-未删除 1-已删除 默认1
	 */
	@NotEmpty
	private Integer isDeleted;
	/**
	 * 合集id
	 */
	@NotEmpty(message = "合集id不能为空", groups = {Add.class, Update.class})
	private Integer ccId;
	/**
	 * 是否通过审核 1-通过 0-未通过 默认0-未通过
	 */
	@NotEmpty(message = "状态不能为空", groups = {Add.class, Update.class})
	private Integer status;
	
}
