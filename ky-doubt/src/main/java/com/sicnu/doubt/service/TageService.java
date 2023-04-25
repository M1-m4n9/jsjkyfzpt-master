package com.sicnu.doubt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.doubt.entity.TageEntity;
import com.sicnu.doubt.entity.TargetUserEntity;

import java.util.Map;

/**
 * @author KingBob
 */
public interface TageService extends IService<TageEntity> {
    PageUtils queryPage(Map<String, Object> params);

}
