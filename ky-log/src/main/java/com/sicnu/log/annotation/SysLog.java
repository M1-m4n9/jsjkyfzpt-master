package com.sicnu.log.annotation;

import java.lang.annotation.*;

/**
 * @Author LiuChuang
 * @Date 2022/11/11 11:21
 * @PackageName:com.sicnu.log.annotation
 * @ClassName: SysLog
 * @Description: 操作日志注解
 * @Version 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
	
	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();
	
	/**
	 * 记录执行参数
	 *
	 * @return
	 */
	boolean recordRequestParam() default true;
	
	/**
	 * 记录返回参数
	 *
	 * @return
	 */
	boolean recordResponseParam() default true;
}
