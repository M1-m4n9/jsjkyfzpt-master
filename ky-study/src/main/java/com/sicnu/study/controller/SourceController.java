package com.sicnu.study.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sicnu.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.study.entity.SourceEntity;
import com.sicnu.study.service.SourceService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;


/**
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@RestController
@RequestMapping("study/source")
public class SourceController
{
	@Autowired
	private SourceService sourceService;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params)
	{
		PageUtils page = sourceService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	
	/**
	 * 信息
	 */
//	@SysLog(value = "嘤嘤嘤")
	@GetMapping("/info")
	public R info(@RequestParam Integer id)
	{
		
		SourceEntity source = sourceService.getSourceById(id);
		if (source == null)
		{
			return R.error("获取用户信息失败");
		}
		return R.ok().put("source", source);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public R save(@RequestBody SourceEntity source)
	{
		//		sourceService.save(source);
		//        return R.ok();
		if (!sourceService.saveSource(source))
		{
			return R.error("添加失败");
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("/update")
	public R update(@RequestBody SourceEntity source)
	{
		//		sourceService.updateById(source);
		
		//        return R.ok();
		if (!sourceService.updateSourceById(source))
		{
			return R.error("修改失败");
		}
		return R.ok();
	}
	
	
	/**
	 * 批量删除
	 */
	@DeleteMapping("delete")
	public R delete(@RequestBody List<Integer> ids)
	{
		//		sourceService.removeByIds(Arrays.asList(ids));
		
		//        return R.ok();
		
		if (!sourceService.removeSourcesByIds(ids))
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
	@DeleteMapping("/{id}")
	public R delete(@PathVariable("id") Integer id)
	{
		if (!sourceService.removeSourceById(id))
		{
			return R.error("删除失败");
		}
		return R.ok();
	}
	
	
	/**
	 * 获取合集中的所有单个资源
	 * @param collectionId
	 * @return
	 */
	@GetMapping("sourcelist")
	public R sourceList(@RequestParam("collectionId") Integer collectionId)
	{
		List<SourceEntity> sourceList = sourceService.getSourceListByCollectionId(collectionId);
		if (sourceList == null)
		{
			return R.error("获取合集中所有资源列表失败");
		}
		return R.ok().put("sourceList", sourceList);
	}
}
