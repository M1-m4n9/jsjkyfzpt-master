package com.sicnu.log.aop;


import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.JwtUtils;
import com.sicnu.common.utils.Payload;
import com.sicnu.common.utils.R;
import com.sicnu.log.dto.OptLogDto;
import com.sicnu.log.entity.CurrentUserEntity;
import com.sicnu.log.properties.RsaKeyProperties;
import com.sicnu.log.service.Impl.OptLogServiceImpl;
import com.sicnu.log.util.LogUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author LiuChuang
 * @Date 2022/11/12 10:15
 * @PackageName:com.sicnu.log.aspect
 * @ClassName: SysAspect
 * @Description: 操作日志切面类
 * @Version 1.0
 */

@Slf4j
@Aspect
//@ComponentScan("com.sicnu.log.dao")
public class SysLogAspect
{
	private OptLogServiceImpl optLogServiceImpl;
	
	private RsaKeyProperties prop;
	
	public void setOptLogServiceImpl(OptLogServiceImpl optLogServiceImpl)
	{
		this.optLogServiceImpl = optLogServiceImpl;
	}
	
	public void setProp(RsaKeyProperties prop) {this.prop = prop;}
	@Pointcut("@annotation(com.sicnu.log.annotation.SysLog)")
	public void pt()
	{
	}
	
	@Around("pt()")
	public Object recordOptLog(ProceedingJoinPoint pjp)
	{
		
		OptLogDto optLogDto = null;
		
		Signature signature = pjp.getSignature();
		//类路径
		String classPath = signature.getDeclaringTypeName();
		//方法名
		String methodName = signature.getName();
		
		Object[] args = pjp.getArgs();
		String strArgs = "";
		HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		try
		{
			if (!request.getContentType().contains("multipart/form-data"))
			{
				strArgs = JSONObject.toJSONString(args);
			}
		} catch (Exception e)
		{
			try
			{
				strArgs = Arrays.toString(args);
			} catch (Exception ex)
			{
				log.warn("解析参数异常", ex);
			}
		}
		String params = getText(strArgs);
		
		String controllerDescription = "";
		Api api = pjp.getTarget().getClass().getAnnotation(Api.class);
		if (api != null)
		{
			String[] tags = api.tags();
			if (tags != null && tags.length > 0)
			{
				controllerDescription = tags[0];
			}
		}
		
		String controllerMethodDescription = LogUtil.getControllerMethodDescription(pjp);
		String description = "";
		if (StrUtil.isEmpty(controllerDescription))
		{
			description = controllerMethodDescription;
		}
		else
		{
			description = controllerDescription + "-" + controllerMethodDescription;
		}
		
		String requestIp = "";
		String requestUri = "";
		String httpMethod = "";
		String ua = "";
		if (request != null)
		{
			requestIp = ServletUtil.getClientIP(request);
			requestUri = URLUtil.getPath(request.getRequestURI());
			httpMethod = request.getMethod();
			ua = StrUtil.sub(request.getHeader("user-agent"), 0, 500);
		}
		
		LocalDateTime startTime = LocalDateTime.now();
		
		optLogDto = OptLogDto.builder().classPath(classPath).methodName(methodName).params(params).description(description).requestIp(requestIp).requestUri(requestUri).httpMethod(httpMethod).ua(ua).startTime(startTime).build();
		
		
		/**
		 * 正常执行和异常执行
		 */
		R r = null;
		try
		{
			r = (R) pjp.proceed();
			if (r == null)
			{
				optLogDto.setType(OptLogDto.TYPE_OPT);
			}
			else
			{
				if (r.getCode() == 0)    //成功
				{
					optLogDto.setType(OptLogDto.TYPE_OPT);
				}
				else                    //失败
				{
					optLogDto.setType(OptLogDto.TYPE_EX);
				}
			}
			optLogDto.setResult(getText(r.toString()));
		} catch (Throwable e)
		{
			//异常堆栈
			optLogDto.setExDetail(LogUtil.getStackTrace(e));
			
			//异常信息
			optLogDto.setExDesc(e.getMessage());
		}
		
		optLogDto.setEndTime(LocalDateTime.now());
		optLogDto.setConsumingTime(optLogDto.getStartTime().until(optLogDto.getEndTime(), ChronoUnit.MILLIS));
		
		
		//解析token
		String token = request.getHeader(Constant.AUTH_TOKEN_HEADER_NAME);
		if (token != null)
		{
			Payload<CurrentUserEntity> payload = JwtUtils.getInfoFromToken(token.replace(Constant.AUTH_TOKEN_HEADER_RESOURCE_START, ""), prop.getPublicKey(), CurrentUserEntity.class);
			CurrentUserEntity currentUser = payload.getUserInfo();
			
			String phone = currentUser.getPhone();
			String userName = currentUser.getUsername();
			optLogDto.setPhone(phone);
			optLogDto.setUserName(userName);
		}
		
		
		//		optLogDao.insert(optLogDto);
		optLogServiceImpl.save(optLogDto);
		// TODO optLogDto怎么加入userID和userName?应该不是Feign，参数解析器试试？,token解析然后封装user信息
		
		
		r.put("optLogDto", optLogDto);
		
		return r;
	}
	
	/**
	 * 截取指定长度的字符串
	 *
	 * @param val
	 * @return
	 */
	private String getText(String val)
	{
		return StrUtil.sub(val, 0, 255);
	}
	
}
