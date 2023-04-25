package com.sicnu.log.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author LiuChuang
 * @Date 2022/11/12 10:03
 * @PackageName:com.sicnu.log.entity
 * @ClassName: OptLogDto
 * @Description: 系统日志实体类
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@Builder
@TableName("tb_syslog")
public class OptLogDto
{
	private static final long serialVersionUID = 1L;
	
	@TableField(exist = false)
	public static final String TYPE_OPT= "OPT";
	@TableField(exist = false)
	public static final String TYPE_EX= "EX";
	
	@TableId
	private Integer id;
	
	/**
	 * 类路径
	 */
	private String classPath;
	
	/**
	 * 方法名
	 */
	private String methodName;
	
	/**
	 * 请求参数
	 */
	private String params;
	
	/**
	 * 操作描述
	 */
	private String description;
	
	/**
	 * 请求IP
	 */
	private String requestIp;
	
	/**
	 * 请求URI
	 */
	private String requestUri;
	
	/**
	 * 请求方式
	 */
	private String httpMethod;
	
	/**
	 * 浏览器
	 */
	private String ua;
	
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;
	
	/**
	 * 消耗时间
	 */
	private Long consumingTime;
	
	/**
	 * 日志类型 OPT还是EX 操作日志还是异常日志
	 */
	private String type;
	
	/**
	 * 异常详情信息
	 */
	private String exDesc;
	
	/**
	 * 异常描述
	 */
	private String exDetail;
	
	/**
	 * 原始执行返回结果
	 */
	private String result;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 用户名
	 */
	private String userName;
}