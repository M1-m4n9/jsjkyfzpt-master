package com.sicnu.study.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sicnu.common.utils.Constant;
import com.sicnu.common.utils.R;
import com.sicnu.log.dto.OptLogDto;
import com.sicnu.log.service.OptLogService;
import com.sicnu.study.entity.CollectionUserEntity;
import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.service.CourseCollectionService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author LiuChuang
 * @Date 2022/11/28 18:21
 * @PackageName:com.sicnu.study.task
 * @ClassName: TaskUtils
 * @Description: TODO
 * @Version 1.0
 */
@Component // 把此类托管给 Spring，不能省略
public class TaskUtils
{
	
	@Autowired
	private OptLogService optLogService;
	
	@Autowired
	private CourseCollectionService courseCollectionService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	// 添加定时任务
	// cron 表达式，每周五 23:59:58 执行
	
	
	//每天一次定时redis点赞收藏量播放浏览量刷新到数据库
	
	@Scheduled(cron = "58 59 23 * * ?")
	public void doLikeAndCollectCountingTask()
	{
		LambdaQueryWrapper<OptLogDto> lqw = new LambdaQueryWrapper<>();
		lqw.eq(OptLogDto::getRequestUri,  "/study/collectionuser/saveorupdate/likeandcollect");
		List<OptLogDto> list = optLogService.list(lqw);
		
		Map<Integer, Integer[]> map = new HashMap<>();
		Integer[] arr = new Integer[2];
		
		for (OptLogDto optLogDto : list)
		{
			String params = optLogDto.getParams();
			String jsonParams = params.replace("[","").replace("]","");
			CollectionUserEntity collectionUserEntity = JSON.parseObject(jsonParams, CollectionUserEntity.class);
			String result = optLogDto.getResult();
			R r = JSON.parseObject(result, R.class);
			
			if (r.getCode() == 0)
			{
				Map<String, Object> likeAndCollect = (Map<String, Object>) r.get("likeAndCollect");
				Integer addLike = (Integer) likeAndCollect.get("addLike");
				Integer addCollect = (Integer) likeAndCollect.get("addCollect");
				Integer cuId = collectionUserEntity.getCollectionId();
				Integer[] cuArr = map.get(cuId);
				if (cuArr == null)
				{
					arr[0] = addLike;
					arr[1] = addCollect;
					map.put(cuId, arr);
				}
				else
				{
					arr[0] = cuArr[0] + addLike;
					arr[1] = cuArr[1] + addCollect;
					map.put(cuId, arr);
				}
			}
		}
		Map<String,Object> params = new HashMap<>();
		for (Integer key : map.keySet())
		{
			params.put("id",key);
			CourseCollectionEntity cc = courseCollectionService.getCCById(params);
			cc.setLikeCount(cc.getLikeCount() + map.get(key)[0]);
			cc.setCollectionCount(cc.getCollectionCount() + map.get(key)[1]);
			courseCollectionService.updateCCById(cc);
			params.remove("id");
		}
		
	}
	
	/**
	 * 定时刷新排行榜前十
	 */
	
	//每天定时从数据库刷新前十（通过点赞收藏播放浏览量之和）到redis中做一个每日排行榜（直接把前十的详情数据弄到redis）
	@Scheduled(cron = "59 59 23 * * ?")
	public void doCourseCollectionTop10Task()
	{
		
		redisTemplate.opsForZSet().removeRange(Constant.COURSE_COLLECTION_TOP_10_VIDEO_ZSET, 0, -1);
		redisTemplate.opsForZSet().reverseRange(Constant.COURSE_COLLECTION_TOP_10_FILE_ZSET, 0, -1);
		//		缓存中前10删除
		redisTemplate.opsForHash().delete(Constant.COURSE_COLLECTION_TOP_10_FILE_HASH);
		redisTemplate.opsForHash().delete(Constant.COURSE_COLLECTION_TOP_10_FILE_HASH);
		//排序通过点赞+收藏+播放or下载
		List<CourseCollectionEntity> list = courseCollectionService.list(null);
		Map<Integer, Integer> zsetMapVideo = new HashMap<>();
		Map<Integer, Integer> zsetMapFile = new HashMap<>();
		list.stream().filter(cc -> cc.getType() == 1).forEach(cc ->{
			zsetMapVideo.put(cc.getId(), cc.getLikeCount() + cc.getCollectionCount() + cc.getPlayCount() + cc.getShareCount());
		});
		list.stream().filter(cc -> cc.getType() == 0).forEach(cc ->{
			zsetMapFile.put(cc.getId(), cc.getLikeCount() + cc.getCollectionCount() + cc.getPlayCount() + cc.getShareCount());
		});
		
		for (Integer id : zsetMapVideo.keySet())
		{
			redisTemplate.opsForZSet().add(Constant.COURSE_COLLECTION_TOP_10_VIDEO_ZSET, String.valueOf(id), zsetMapVideo.get(id));
		}
		for (Integer id : zsetMapFile.keySet())
		{
			redisTemplate.opsForZSet().add(Constant.COURSE_COLLECTION_TOP_10_FILE_ZSET, String.valueOf(id), zsetMapFile.get(id));
		}
		Set<String> zsetVideoS = redisTemplate.opsForZSet().reverseRange(Constant.COURSE_COLLECTION_TOP_10_VIDEO_ZSET, 0, 9);
		List<String> zsetVideoL = new ArrayList<>(zsetVideoS);
		Set<String> zsetFileS = redisTemplate.opsForZSet().reverseRange(Constant.COURSE_COLLECTION_TOP_10_FILE_ZSET, 0, 9);
		List<String> zsetFileL = new ArrayList<>(zsetFileS);
		Collections.reverse(zsetVideoL);
		Collections.reverse(zsetFileL);

		Map<String,Object> params = new HashMap<>();

		for (String s : zsetVideoL)
		{
			params.put("id",Integer.valueOf(s));
			if (redisTemplate.opsForHash().size(Constant.COURSE_COLLECTION_TOP_10_VIDEO_HASH) == 10) {break;}
			redisTemplate.opsForHash().put(Constant.COURSE_COLLECTION_TOP_10_VIDEO_HASH, s, courseCollectionService.getCCById(params));
			params.remove("id");
		}
		for (String s : zsetFileL)
		{
			params.put("id",Integer.valueOf(s));
			if (redisTemplate.opsForHash().size(Constant.COURSE_COLLECTION_TOP_10_FILE_HASH) == 10) {break;}
			redisTemplate.opsForHash().put(Constant.COURSE_COLLECTION_TOP_10_FILE_HASH, s, courseCollectionService.getCCById(params));
			params.remove("id");
		}
	}
}