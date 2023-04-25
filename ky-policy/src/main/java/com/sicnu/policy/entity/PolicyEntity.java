package com.sicnu.policy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * @email 2565477149@qq.com
 * @date 2022-09-14 12:34:12
 */
@Data
@TableName("p_policy")
public class PolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策法规id
	 */
	@TableId
	@NotNull(message = "修改必须指定品牌id",groups = {Update.class})
	@Null(message = "新增不能指定id",groups = {Add.class})
	private Integer id;
	/**
	 * 政策法规文件名字
	 */
	@NotEmpty(message = "政策法规文件名字不能为空",groups = {Update.class,Add.class})
	private String name;
	/**
	 * 省
	 */
	@NotEmpty(message = "省份不能为空",groups = {Update.class,Add.class})
	private String province;
	/**
	 * 资源地址
	 */
	//TODO url路径没有匹配
	@NotEmpty(message = "资源地址不能为空",groups = {Update.class,Add.class})
	private String sourceUrl;
	/**
	 * 上传时间
	 */
	//TODO 日期格式没有校验
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date upTime;

}
