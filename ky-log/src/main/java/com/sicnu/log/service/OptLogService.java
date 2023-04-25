package com.sicnu.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.log.dto.OptLogDto;

import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/13 13:43
 * @PackageName:com.sicnu.log.service
 * @ClassName: OptLogService
 * @Description:
 * @Version 1.0
 */
public interface OptLogService extends IService<OptLogDto>
{
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);
}
