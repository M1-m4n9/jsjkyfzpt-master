package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.UserFollowsEntity;
import com.sicnu.person.vo.FollowsVo;
import com.sicnu.person.vo.UserUnfollowVo;

import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 13:17
 * @PackageName:com.sicnu.person.service
 * @ClassName: UserFollowsService
 * @Description: TODO
 * @Version 1.0
 */
public interface UserFollowsService extends IService<UserFollowsEntity>
{
	/**
	 * 点击关注单个用户
	 * @param userFollowsEntity
	 * @return
	 */
	void follow(UserFollowsEntity userFollowsEntity);
	
	/**
	 * 获取用户关注列表
	 * @param params
	 * @return
	 */
//	PageUtils queryFollowPage(Map<String, Object> params);
	FollowsVo queryFollowPage(Map<String, Object> params);
	
	/**
	 * 获取用户粉丝列表
	 * @param params
	 * @return
	 */
	FollowsVo queryFansPage(Map<String, Object> params);
	
	/**
	 * 点击取消用户关注(批量)
	 * @param userUnfollowVo
	 */
	void unfollow(UserUnfollowVo userUnfollowVo);
}
