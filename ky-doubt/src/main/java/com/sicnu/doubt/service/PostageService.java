package com.sicnu.doubt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.doubt.entity.PostageEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * (DPostage)表服务接口
 *
 * @author KingBob
 * @since 2022-12-13 00:18:40
 */
public interface PostageService extends IService<PostageEntity> {
    PageUtils queryPage(Map<String, Object> params);

    List<Integer> selectByPid(Integer pid);
}

