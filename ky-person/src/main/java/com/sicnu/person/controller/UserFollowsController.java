package com.sicnu.person.controller;

import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.person.entity.UserFollowsEntity;
import com.sicnu.person.service.UserFollowsService;
import com.sicnu.person.vo.FollowsVo;
import com.sicnu.person.vo.UserUnfollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 13:19
 * @PackageName:com.sicnu.person.controller
 * @ClassName: UserFollowsController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("person/userfollows")
public class UserFollowsController
{
	@Autowired
	private UserFollowsService userFollowsService;
	
	/**
	 * 点击关注单个用户
	 */
	@PostMapping("/save")
	public R save(@RequestBody UserFollowsEntity userFollowsEntity)
	{
		userFollowsService.follow(userFollowsEntity);
		return R.ok();
	}
	
	/**
	 * 点击取消用户关注(批量)
	 * @param userUnfollowVo
	 * @return
	 */
	@DeleteMapping("/delete")
	public R delete(@RequestBody UserUnfollowVo userUnfollowVo)
	{
		userFollowsService.unfollow(userUnfollowVo);
		return R.ok();
	}
	
	/**
	 * 获取用户关注列表
	 * @return
	 */
	@GetMapping("list/follow")
	public R listFollow(@RequestParam Map<String, Object> params)
	{
//		PageUtils page = userFollowsService.queryFollowPage(params);
		FollowsVo vo = userFollowsService.queryFollowPage(params);
		return R.ok().put("page", vo);
	}
	
	/**
	 * 获取用户粉丝列表
	 * @param params
	 * @return
	 */
	@GetMapping("list/fans")
	public R listFans(@RequestParam Map<String, Object> params)
	{
		FollowsVo page = userFollowsService.queryFansPage(params);
		return R.ok().put("page", page);
	}
}
