package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.RolePowerEntity;

import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
public interface RolePowerService extends IService<RolePowerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

