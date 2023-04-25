package com.sicnu.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.study.entity.CollectionUserEntity;

import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/28 11:14
 * @PackageName:com.sicnu.study.service
 * @ClassName: CollectionUserService
 * @Description: TODO
 * @Version 1.0
 */
public interface CollectionUserService extends IService<CollectionUserEntity>
{
	/**
	 * 点赞或收藏资源合集与否
	 */
	boolean likeAndCollect(CollectionUserEntity collectionUserEntity);
	
	/**
	 * 获取我的收藏列表(收藏视频或者收藏文本）
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	CollectionUserEntity selectByUTT(CollectionUserEntity collectionUserEntity);
}
