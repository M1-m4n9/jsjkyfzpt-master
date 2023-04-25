package com.sicnu.doubt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.doubt.entity.TargetUserEntity;

import java.util.Map;

/**
 * (DTargetUser)表服务接口
 *
 * @author makejava
 * @since 2022-12-15 01:12:05
 */
public interface TargetUserService extends IService<TargetUserEntity> {

    /**
     * 获取收藏列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取当前用户点赞收藏情况
     * @param targetUserEntity
     * @return
     */
    TargetUserEntity selectByUTT(TargetUserEntity targetUserEntity);

    /**
     * 点赞收藏接口
     * @param targetUserEntity
     * @return
     */
    boolean likeCollect(TargetUserEntity targetUserEntity);

    /**
     * redis跟新数据库
     * @param uid
     * @param entityId
     * @param type
     * @param liked
     * @param collected
     * @return
     */
    boolean updateByR(Integer uid, Integer entityId,Integer type,Integer liked,Integer collected);
}

