package com.sicnu.log.config;



import com.sicnu.log.aop.SysLogAspect;
import com.sicnu.log.properties.RsaKeyProperties;
import com.sicnu.log.service.Impl.OptLogServiceImpl;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author LiuChuang
 * @Date 2022/11/12 12:26
 * @PackageName:com.sicnu.log
 * @ClassName: LogAutoConfiguration
 * @Description: TODO 注入optLogServiceImpl
 * @Version 1.0
 */

@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "jsjkyfzpt.syslog.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan("com.sicnu.log.service")
@MapperScan(basePackages = "com.sicnu.log.dao")
public class LogAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public SysLogAspect sysLogAspect(@Autowired OptLogServiceImpl optLogServiceImpl)
	{
		SysLogAspect sysLogAspect = new SysLogAspect();
		sysLogAspect.setOptLogServiceImpl(optLogServiceImpl);
		sysLogAspect.setProp(new RsaKeyProperties());
		return sysLogAspect;
	}
}