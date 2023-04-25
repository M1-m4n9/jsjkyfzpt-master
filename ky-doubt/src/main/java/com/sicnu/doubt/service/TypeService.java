package com.sicnu.doubt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.doubt.entity.TypeEntity;

import java.util.Map;

/**
 *
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

