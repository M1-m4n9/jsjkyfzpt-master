package com.sicnu.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.person.dao.UserFollowsDao;
import com.sicnu.person.entity.UserEntity;
import com.sicnu.person.entity.UserFollowsEntity;
import com.sicnu.person.service.UserFollowsService;
import com.sicnu.person.service.UserService;
import com.sicnu.person.vo.FollowsVo;
import com.sicnu.person.vo.UserFollowsVo;
import com.sicnu.person.vo.UserUnfollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 13:18
 * @PackageName:com.sicnu.person.service.impl
 * @ClassName: UserFollowsServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service("userFollowsService")
public class UserFollowsServiceImpl extends ServiceImpl<UserFollowsDao, UserFollowsEntity> implements UserFollowsService
{
	
	@Autowired
	private UserFollowsService userFollowsService;
	
	@Autowired
	private UserService userService;


	
	/**
	 * 点击关注单个用户
	 * @param userFollowsEntity
	 * @return
	 */
	@Override
	@Transactional
	public void follow(UserFollowsEntity userFollowsEntity)
	{
		Integer uid = userFollowsEntity.getUid();
		Integer followedUid = userFollowsEntity.getFollowedUid();
		if (uid.intValue() == followedUid.intValue())
		{
			throw new KyException(CodeEnume.FOLLOW_ERROR);
		}
		/**
		 * 更新user_follows表
		 */
		/*DYB*/
		LambdaQueryWrapper<UserFollowsEntity> dyblqw = new LambdaQueryWrapper<>();
		dyblqw.eq(UserFollowsEntity::getUid,userFollowsEntity.getUid())
				.eq(UserFollowsEntity::getFollowedUid,userFollowsEntity.getFollowedUid());
		if (baseMapper.selectOne(dyblqw)!=null) {
			return;
		}
		/*DYB*/

		LambdaQueryWrapper<UserFollowsEntity> lqw = new LambdaQueryWrapper<>();
		lqw.eq(UserFollowsEntity::getUid, followedUid)
				.eq(UserFollowsEntity::getFollowedUid, uid);
		UserFollowsEntity myFans = baseMapper.selectOne(lqw);
		if (myFans != null)
		{
			myFans.setStatus(1);
			userFollowsEntity.setStatus(1);
			baseMapper.updateById(myFans);
		}
		if(myFans == null){
			userFollowsEntity.setStatus(0);
		}else {
			userFollowsEntity.setStatus(1);
		}
		baseMapper.insert(userFollowsEntity);
		

		/**
		 * 更新用户表
		 */
		LambdaQueryWrapper<UserEntity> lqw1 = new LambdaQueryWrapper<>();
		lqw1.eq(UserEntity::getId, uid);
		UserEntity my = userService.getOne(lqw1);
		my.setFollowCount(my.getFollowCount() + 1);
		userService.updateById(my);
		
		LambdaQueryWrapper<UserEntity> lqw2 = new LambdaQueryWrapper<>();
		lqw2.eq(UserEntity::getId, followedUid);
		UserEntity your = userService.getOne(lqw2);
		your.setFansCount(your.getFansCount() + 1);
		userService.updateById(your);
	}
	
	/**
	 * 获取用户关注列表
	 * @param params
	 * @return
	 */

	/**
	 * lb修改
	 * 修改了一下因为最开始返回的是单纯的id数据实现上需要头像，个人介绍，用户名
	 * @param params
	 * @return
	 */
	@Override
	public FollowsVo queryFollowPage(Map<String, Object> params)
//	public PageUtils queryFollowPage(Map<String, Object> params)
	{
		String uid = (String) params.get(Constant.UID);
		LambdaQueryWrapper<UserFollowsEntity> lqw = new LambdaQueryWrapper<>();
		if(uid == null || uid.isEmpty()) return null;
		if (uid != null && !uid.isEmpty() && Integer.valueOf(uid) != 0) {lqw.eq(UserFollowsEntity::getUid, uid);}
		IPage<UserFollowsEntity> page = this.page(new Query<UserFollowsEntity>().getPage(params, "create_time", false), lqw);
		List<UserFollowsEntity> records = page.getRecords();
		List<Integer> userIds = new ArrayList<>();
		HashMap<Integer,Integer> map = new HashMap();
		records.forEach(record->{
			userIds.add(record.getFollowedUid());
			map.put(record.getFollowedUid(), record.getStatus());
		});
		if(userIds.size() == 0 || userIds.isEmpty()) return null;
		List<UserEntity> users = userService.listByIds(userIds);
		if(users.size() == 0 || users.isEmpty()) return null;
		List<UserFollowsVo> res = new ArrayList<>();
		users.forEach(user -> {
			UserFollowsVo vo = new UserFollowsVo();
			vo.setUid(user.getId());
			vo.setUsername(user.getUsername());
			vo.setHeadUrl(user.getHeadUrl());
			vo.setIntroduce(user.getIntroduction());
			vo.setStatus(map.getOrDefault(user.getId(),0));
			res.add(vo);
		});
		long pages = page.getPages();
		long size = page.getSize();
		long current = page.getCurrent();
		long total = page.getTotal();
		FollowsVo vo = new FollowsVo();
		vo.setRecords(res);
		vo.setPages(pages);
		vo.setSize(size);
		vo.setCurrent(current);
		vo.setTotal(total);
//		return new PageUtils(page);
		return vo;
	}
	
	/**
	 * 获取用户粉丝列表
	 * @param params
	 * @return
	 */

	/**
	 * lb修改
	 * 与上面同理
	 * @param params
	 * @return
	 */
	@Override
	public FollowsVo queryFansPage(Map<String, Object> params)
	{
		String uid = (String) params.get(Constant.UID);
		LambdaQueryWrapper<UserFollowsEntity> lqw = new LambdaQueryWrapper<>();
		if (uid != null && !uid.isEmpty() && Integer.valueOf(uid) != 0) {lqw.eq(UserFollowsEntity::getFollowedUid, uid);}
		IPage<UserFollowsEntity> page = this.page(new Query<UserFollowsEntity>().getPage(params, "create_time", false), lqw);

		//查询出其粉丝记录
		List<UserFollowsEntity> records = page.getRecords();


		List<Integer> userIds = new ArrayList<>();
		HashMap<Integer,Integer> map = new HashMap();

		//讲谁关注了保存下来
		records.forEach(record->{
			userIds.add(record.getUid());
			map.put(record.getUid(), record.getStatus());
		});
		FollowsVo vo = new FollowsVo();

		if(userIds.size() == 0 || userIds.isEmpty()) return vo;
		List<UserEntity> users = userService.listByIds(userIds);
		if(users.size() == 0 || users.isEmpty()) return vo;
		List<UserFollowsVo> res = new ArrayList<>();
		users.forEach(user -> {
			UserFollowsVo uo = new UserFollowsVo();
			uo.setUid(user.getId());
			uo.setUsername(user.getUsername());
			uo.setHeadUrl(user.getHeadUrl());
			uo.setIntroduce(user.getIntroduction());
			uo.setStatus(map.getOrDefault(user.getId(),0));
			res.add(uo);
		});
		long pages = page.getPages();
		long size = page.getSize();
		long current = page.getCurrent();
		long total = page.getTotal();

		vo.setRecords(res);
		vo.setPages(pages);
		vo.setSize(size);
		vo.setCurrent(current);
		vo.setTotal(total);
//		return new PageUtils(page);
		return vo;
	}
	
	
	/**
	 * 点击取消用户关注(批量)
	 * @param userUnfollowVo
	 */
	@Override
	@Transactional
	public void unfollow(UserUnfollowVo userUnfollowVo)
	{
		Integer uid = userUnfollowVo.getUid();
		Integer[] ids = userUnfollowVo.getIds();
		int length = ids.length;
		
		/**
		 * 更新用户关注表
		 */
		LambdaQueryWrapper<UserFollowsEntity> lqw = new LambdaQueryWrapper<>();
		lqw.eq(UserFollowsEntity::getUid, uid);
		lqw.in(UserFollowsEntity::getFollowedUid, ids);
		baseMapper.delete(lqw);
		/*DYB*/
		LambdaQueryWrapper<UserFollowsEntity> lqw2 = new LambdaQueryWrapper<>();
		lqw2.in(UserFollowsEntity::getUid,ids);
		lqw2.eq(UserFollowsEntity::getFollowedUid,uid);
		lqw2.eq(UserFollowsEntity::getStatus,1);
		UserFollowsEntity userFollowsEntity = new UserFollowsEntity();
		userFollowsEntity.setStatus(0);
		baseMapper.update(userFollowsEntity,lqw2);
		/**
		 * 更新用户表
		 */
		LambdaQueryWrapper<UserEntity> lqw1 = new LambdaQueryWrapper<>();
		lqw1.eq(UserEntity::getId, uid);
		UserEntity my = userService.getOne(lqw1);
		my.setFollowCount(my.getFollowCount() - length);
		userService.updateById(my);
		
		List<UserEntity> userEntities = userService.listByIds(Arrays.asList(ids));
		userEntities.stream().forEach(u -> u.setFansCount(u.getFansCount() - 1));
		userService.updateBatchById(userEntities);
	}
	
}
