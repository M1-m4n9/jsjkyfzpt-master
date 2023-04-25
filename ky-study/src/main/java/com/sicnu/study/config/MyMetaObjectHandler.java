package com.sicnu.study.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 自定义元数据对象处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{
	/**
	 * 插入操作，自动填充
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject)
	{
		//		log.info("公共字段自动填充[insert]...");
		//		log.info(metaObject.toString());
//		metaObject.setValue("createTime", LocalDateTime.now());
//		this.setFieldValByName("createTime",LocalDateTime.now().toLocalDate(),metaObject);
		this.setFieldValByName("createTime", Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),metaObject);
		this.setFieldValByName("topTime",Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),metaObject);
//		metaObject.setValue("updateTime", LocalDateTime.now());
//		metaObject.setValue("createUser", BaseContext.getCurrentId());
//		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}
	
	@Override
	public void updateFill(MetaObject metaObject)
	{
		//		log.info("公共字段自动填充[update]...");
		//		log.info(metaObject.toString());
		
		//		long id = Thread.currentThread().getId();
		//		log.info("线程id为：{}", id);
		
//		metaObject.setValue("updateTime", LocalDateTime.now());
//		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}
}
