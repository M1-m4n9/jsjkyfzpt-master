package com.sicnu.study.controller;

import com.sicnu.common.utils.R;
import com.sicnu.study.entity.CollectionClassificationEntity;
import com.sicnu.study.service.CollectionClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author LiuChuang
 * @Date 2022/11/21 13:54
 * @PackageName:com.sicnu.study.controller
 * @ClassName: ColletionClassificationsController
 * @Description: 合集分类CRUD
 * @Version 1.0
 */
@RestController
@RequestMapping("study/collectionClassfication")
public class CollectionClassificationController
{
	@Autowired
	private CollectionClassificationService collectionClassificationService;
	
	@GetMapping("/list")
	public R getClassificationList()
	{
		List<CollectionClassificationEntity> list = collectionClassificationService.list();
		return R.ok().put("data", list);
	}
	
	/**
	 * 删除省份
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer ids[])
	{
		collectionClassificationService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("/update")
	public R update(@RequestBody CollectionClassificationEntity collectionClassificationEntity)
	{
		collectionClassificationService.updateById(collectionClassificationEntity);
		return R.ok();
	}
	
	/**
	 * 增加
	 */
	@PostMapping("/save")
	public R add(@RequestBody CollectionClassificationEntity collectionClassificationEntity)
	{
		collectionClassificationService.save(collectionClassificationEntity);
		return R.ok();
	}
}
