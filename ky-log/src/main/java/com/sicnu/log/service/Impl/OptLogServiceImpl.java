package com.sicnu.log.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.log.dao.OptLogDao;
import com.sicnu.log.dto.OptLogDto;
import com.sicnu.log.service.OptLogService;
import org.springframework.stereotype.Service;


import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/13 13:44
 * @PackageName:com.sicnu.log.service.Impl
 * @ClassName: OptLogServiceImpl
 * @Description:
 * @Version 1.0
 */
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogDao, OptLogDto> implements OptLogService
{
	/**
	 * 分页+条件查询
	 * @param params
	 * @return
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params)
	{
		String userId = (String) params.get(Constant.USER_ID);
		String userName = (String) params.get(Constant.USER_NAME);
		String type = (String) params.get(Constant.LOG_TYPE);
		String requestIp = (String) params.get(Constant.REQUEST_IP);
		String httpMethod = (String) params.get(Constant.HTTP_METHOD);
		String startTime = (String) params.get(Constant.LOG_START_TIME);
		String endTime = (String) params.get(Constant.LOG_END_TIME);
//		Long consumingTime = (Long) params.get(Constant.CONSUMING_TIME);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date st;
//		Date ed;
//		if (startTime != null && !"".equals(StrUtil.trim(startTime))) {
//			try
//			{
//				st = simpleDateFormat.parse(startTime);
//			} catch (ParseException e)
//			{
//				throw new RuntimeException(e);
//			}
//		}
		
		String ua = (String) params.get(Constant.USER_AGENT);
		
		QueryWrapper<OptLogDto> qw = new QueryWrapper<>();
		if (userId != null && !"".equals(StrUtil.trim(userId)) && Integer.valueOf(StrUtil.trim(userId)) != 0) {qw.eq("user_id", Integer.valueOf(StrUtil.trim(userId)));}
		if (userName != null && !"".equals(StrUtil.trim(userName))) {qw.eq("user_name", StrUtil.trim(userName));}
		if (type != null && !"".equals(StrUtil.trim(type))) {qw.eq("type", StrUtil.trim(type));}
		if (requestIp != null && !"".equals(StrUtil.trim(requestIp))) {qw.eq("request_ip", StrUtil.trim(requestIp));}
		if (httpMethod != null && !"".equals(StrUtil.trim(httpMethod))) {qw.eq("http_method", StrUtil.trim(httpMethod));}
//		if (startTime != null && !"".equals(startTime)) {qw.ge("start_time", startTime);}
//		if (endTime != null && !"".equals(endTime)) {qw.ge("end_time", endTime);}
		
		if (ua != null && !"".equals(StrUtil.trim(ua))) {qw.like("ua", StrUtil.trim(ua));}
//		IPage<OptLogDto> page = this.page(new Query<OptLogDto>().getPage(params), qw);
		IPage<OptLogDto> page = this.page(new Query<OptLogDto>().getPage(params, "start_time", false), qw);
		return new PageUtils(page);
	}
}
