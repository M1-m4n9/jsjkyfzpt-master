package com.sicnu.study.controller;

import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.log.annotation.SysLog;
import com.sicnu.study.entity.CollectionUserEntity;
import com.sicnu.study.service.CollectionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UID;
import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/28 11:17
 * @PackageName:com.sicnu.study.controller
 * @ClassName: CollectionUserController
 * @Description: TODO
 * @Version 1.0
 */

@RestController
@RequestMapping("/study/collectionuser")
public class CollectionUserController
{
	@Autowired
	private CollectionUserService collectionUserService;
	
	/**
	 * 点赞或收藏资源合集与否
	 */
//	@SysLog("对资源合集的点赞和收藏")
	@PostMapping("/saveorupdate/likeandcollect")
	public R saveOrUpdate(@RequestBody CollectionUserEntity collectionUserEntity)
	{
		Integer uid = collectionUserEntity.getUid();
		if(uid==null){
			return R.error("请先登录");
		}
//		try {
			collectionUserService.likeAndCollect(collectionUserEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return R.error("系统错误");
//		}
		return R.ok();
	}
	
	/**
	 * 获取我的收藏列表(收藏视频或者收藏文本）
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params)
	{
		PageUtils page = collectionUserService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 获取点赞收藏数据
	 * @param params
	 * @return
	 */
	@GetMapping("/likclct")
	public R info(@RequestParam Map<String,Object> params) {
		Integer uidp = Integer.valueOf(String.valueOf(params.get("uid")));
		Integer collectionIdp = Integer.valueOf(String.valueOf(params.get("collectionId")));
		Integer collectionTypep = Integer.valueOf(String.valueOf(params.get("collectionType")));
		CollectionUserEntity collectionUserEntity = new CollectionUserEntity();
		collectionUserEntity.setUid(uidp);
		collectionUserEntity.setCollectionId(collectionIdp);
		collectionUserEntity.setCollectionType(collectionTypep);


		CollectionUserEntity tue = null;
		Integer uid = collectionUserEntity.getUid();
		Integer entityId = collectionUserEntity.getCollectionId();
		Integer type = collectionUserEntity.getCollectionType();
		if(entityId ==null || type==null){
			return R.error("系统异常");
		}else if(uid==null){
			return R.error("请先登录");
		}
		try {
			tue = collectionUserService.selectByUTT(collectionUserEntity);
			if(tue==null) {
				return R.error("参数错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常");
		}
		return R.ok().put("collectionUse",tue);
	}

	
}
