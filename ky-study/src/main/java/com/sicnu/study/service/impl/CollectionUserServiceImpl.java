package com.sicnu.study.service.impl;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.study.dao.CollectionUserDao;
import com.sicnu.study.entity.CollectionUserEntity;
import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.service.CollectionUserService;
import com.sicnu.study.service.CourseCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sicnu.common.utils.Constant.*;
import static com.sicnu.common.utils.Constant.ENTITY_ID;

/**
 * @Author LiuChuang
 * @Date 2022/11/28 11:15
 * @PackageName:com.sicnu.study.service.impl
 * @ClassName: CollectionUserServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service("collectionUserService")
public class CollectionUserServiceImpl extends ServiceImpl<CollectionUserDao, CollectionUserEntity> implements CollectionUserService
{
	@Autowired
	private CourseCollectionService courseCollectionService;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	@Transactional
	public boolean likeAndCollect(CollectionUserEntity collectionUserEntity)
	{
		Integer uid = collectionUserEntity.getUid();
		Integer entityId = collectionUserEntity.getCollectionId();
		Integer type = collectionUserEntity.getCollectionType();
		Integer like = collectionUserEntity.getLiked();
		Integer collecte = collectionUserEntity.getCollected();

		if(entityId==null){
			return false;
		}

		//总点赞，收藏
		String redis =null;
		if(type==1){
			redis=rediskeycon(entityId,STUDY_COLLECTION);
		}else if(type==0){
			redis=rediskeycon(entityId,STUDY_PAPER);
		}else{
			redis=rediskeycon(entityId,STUDY_COMMENT);
		}
		if(like!=null){
			redisTemplate.opsForHash().increment(redis,LIKE_RECORD,like==0?-1:1);
		}
		if(collecte!=null){
			redisTemplate.opsForHash().increment(redis,COLLECT_RECORD,collecte==0?-1:1);
		}

		String rediskeycon = rediskeycon(STUDY,uid,entityId,type);

		if (Boolean.TRUE.equals(redisTemplate.hasKey(rediskeycon))) {
			if(collecte!=null) {
				CollectionUserEntity couselect = baseMapper.selectByUTT(uid, entityId, type);
				couselect.setCollected(collecte);
				baseMapper.updateById(couselect);
			}

			Long expire = redisTemplate.getExpire(rediskeycon, TimeUnit.MINUTES);
			if(like!=null){
				redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,like.toString());
			}
			if(collecte!=null){
				redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collecte.toString());
			}
			redisTemplate.opsForHash().put(rediskeycon,ENTITY_ID,entityId.toString());
			if(241-expire<30){
				redisTemplate.expire(rediskeycon,expire-1, TimeUnit.MINUTES);
			}else{
				redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
			}

		}else{
			CollectionUserEntity database = baseMapper.selectByUTT(uid, entityId, type);
			if(database!=null){
				collectionUserEntity.setId(database.getId());
				baseMapper.updateById(collectionUserEntity);
			}else{
				baseMapper.insert(collectionUserEntity);
			}
			if(like!=null){
				redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,like.toString());
			}else{
				redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,String.valueOf(0));
			}
			if(collecte!=null){
				redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collecte.toString());
			}else{
				redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,String.valueOf(0));
			}
			redisTemplate.opsForHash().put(rediskeycon,ENTITY_ID,entityId.toString());
			redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
		}

		return true;

	}
	
	/**
	 * 获取我的收藏列表(收藏视频或者收藏文本）
	 * @param params
	 * @return
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params)
	{
		String uid = String.valueOf(params.get(UID));
		String type = String.valueOf(params.get(COLLECTION_TYPE));


		LambdaQueryWrapper<CollectionUserEntity> lqw = new LambdaQueryWrapper<>();
		if (uid != null && !uid.isEmpty() && Integer.parseInt(uid) != 0) {lqw.eq(CollectionUserEntity::getUid, Integer.valueOf(uid));}
		lqw.eq(CollectionUserEntity::getCollected,1);
		if (type != null && !type.isEmpty()) {lqw.eq(CollectionUserEntity::getCollectionType, Integer.parseInt(type));}


		IPage<CollectionUserEntity> page = this.page(new Query<CollectionUserEntity>().getPage(params, "create_time", false), lqw);
		IPage<CourseCollectionEntity> pageInfo = new Page<>();
		pageInfo.setPages(page.getPages());//总页数
		pageInfo.setTotal(page.getTotal());//总记录数
		pageInfo.setCurrent(page.getCurrent());//当前页数
		pageInfo.setSize(page.getSize());//每页记录数
		
		List<CollectionUserEntity> records = page.getRecords();
		List<CourseCollectionEntity> newRecords = new ArrayList<>();
		Map<String,Object> paramss = new HashMap<>();
		paramss.put(UID,Integer.parseInt(uid));
		for (CollectionUserEntity record : records)
		{
			paramss.put("id",record.getCollectionId());
			CourseCollectionEntity ccById = courseCollectionService.getCCById(paramss);
			newRecords.add(ccById);
			paramss.remove("id");
		}
		pageInfo.setRecords(newRecords);
		
		return new PageUtils(pageInfo);
	}

	@Override
	public CollectionUserEntity selectByUTT(CollectionUserEntity collectionUserEntity) {
		Integer uid = collectionUserEntity.getUid();
		Integer entityId = collectionUserEntity.getCollectionId();
		Integer type = collectionUserEntity.getCollectionType();
		CollectionUserEntity tue = null;
		collectionUserEntity.setLiked(0);
		collectionUserEntity.setCollected(0);

		if(uid!=0 && entityId!=0){
			String rediskeycon = rediskeycon(STUDY,uid,entityId,type);

			Object olike = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
			Object ocollect = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
			String like = String.valueOf(olike);
			String collect = String.valueOf(ocollect);

			if(olike!=null&&ocollect!=null){
				collectionUserEntity.setLiked(Integer.parseInt(like));
				collectionUserEntity.setCollected(Integer.parseInt(collect));
				return collectionUserEntity;
			}else if(olike!=null||ocollect!=null){
				tue = baseMapper.selectByUTT(uid, entityId, type);
				if(tue==null){
					collectionUserEntity.setLiked(olike==null?1:Integer.parseInt(like));
					collectionUserEntity.setCollected(ocollect==null?1:Integer.parseInt(collect));
					baseMapper.insert(collectionUserEntity);
					return collectionUserEntity;
				}else{
					Integer likef = olike == null ? tue.getLiked() : Integer.parseInt(like);
					Integer collectf = ocollect == null ? tue.getCollected() : Integer.parseInt(collect);
					redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,likef.toString());
					redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collectf.toString());
					redisTemplate.opsForHash().put(rediskeycon,COLLECTION_ID,entityId.toString());
					redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);

					collectionUserEntity.setLiked(likef);
					collectionUserEntity.setCollected(collectf);
					return collectionUserEntity;
				}
			}else{
				tue = baseMapper.selectByUTT(uid,entityId,type);
				if(tue==null){
					baseMapper.insert(collectionUserEntity);
					redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,String.valueOf(0));
					redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,String.valueOf(0));
					redisTemplate.opsForHash().put(rediskeycon,COLLECTION_ID,entityId.toString());
					redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
					return collectionUserEntity;
				}else{
					redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,tue.getLiked().toString());
					redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,tue.getCollected().toString());
					redisTemplate.opsForHash().put(rediskeycon,COLLECTION_ID,entityId.toString());
					redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
					return collectionUserEntity;
				}

			}

		}
		return tue;
	}


}
