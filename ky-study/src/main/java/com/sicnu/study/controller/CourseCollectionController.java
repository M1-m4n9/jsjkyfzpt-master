package com.sicnu.study.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.sicnu.common.utils.Constant;
import com.sicnu.study.vo.CollectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.service.CourseCollectionService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;


/**
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@RestController
@RequestMapping("study/coursecollection")
public class CourseCollectionController
{
	@Autowired
	private CourseCollectionService courseCollectionService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params)
	{
		PageUtils page = courseCollectionService.queryPage(params);
		
		return R.ok().put("page", page);
	}
	
	/**
	 * 获取排行榜前10（直接redis中获取前十的详细数据）
	 */
	@GetMapping("/list/top10")
	public R getTop10(@RequestParam Integer type)
	{
		Set<String> zsetVideoS = redisTemplate.opsForZSet().reverseRange(Constant.COURSE_COLLECTION_TOP_10_VIDEO_ZSET, 0, 9);
		List<String> zsetVideoL = new ArrayList<>(zsetVideoS);
		Set<String> zsetFileS = redisTemplate.opsForZSet().reverseRange(Constant.COURSE_COLLECTION_TOP_10_FILE_ZSET, 0, 9);
		List<String> zsetFileL = new ArrayList<>(zsetFileS);
		Collections.reverse(zsetVideoL);
		Collections.reverse(zsetFileL);
		List<CourseCollectionEntity> listTop10 = new ArrayList<>();
		if (type ==1)
		{
			for (String s : zsetVideoL)
			{
				CourseCollectionEntity courseCollection = JSON.parseObject((String) redisTemplate.opsForHash().get(Constant.COURSE_COLLECTION_TOP_10_VIDEO_HASH, s), CourseCollectionEntity.class);
				listTop10.add(courseCollection);
			}
		}
		else
		{
			for (String s : zsetFileL)
			{
				CourseCollectionEntity courseCollection = JSON.parseObject((String) redisTemplate.opsForHash().get(Constant.COURSE_COLLECTION_TOP_10_FILE_HASH, s), CourseCollectionEntity.class);
				listTop10.add(courseCollection);
			}
		}
		return R.ok().put("Top10", listTop10);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/info")
	public R info(@RequestParam Map<String,Object> params)
	{
		CollectionVo collectionVo = courseCollectionService.getCCById(params);
		
		return R.ok().put("collectionAndList", collectionVo);
	}
	
//	@GetMapping("/sourelist")
//	publi
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public R save(@RequestBody CourseCollectionEntity courseCollection)
	{
		if (!courseCollectionService.saveCC(courseCollection))
		{
			return R.error("添加失败");
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("/update")
	public R update(@RequestBody CourseCollectionEntity courseCollection)
	{
		if (!courseCollectionService.updateCCById(courseCollection))
		{
			return R.error("修改失败");
		}
		return R.ok();
	}
	
	/**
	 * 批量删除
	 */

	@DeleteMapping("/delete")
	public R delete(@RequestBody Integer[] ids)
	{
		if (!courseCollectionService.removeCCsByIds(ids))
		{
			return R.error("批量删除失败");
		}
		return R.ok();
	}
	
	/**
	 * 删除单个
	 * @param id
	 * @return
	 */
//	@DeleteMapping("/delete")
//	public R delete(@RequestParam("id") Integer id)
//	{
//		if (!courseCollectionService.removeCCById(id))
//		{
//			return R.error("删除失败");
//		}
//		return R.ok();
//	}
}
