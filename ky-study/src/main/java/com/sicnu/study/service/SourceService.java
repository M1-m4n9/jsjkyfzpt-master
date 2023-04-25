package com.sicnu.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.study.entity.SourceEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
public interface SourceService extends IService<SourceEntity> {
	
	/**
	 * 新增学习资源
	 * @param source
	 */
	Boolean saveSource(SourceEntity source);
	
	/**
	 * 更新学习资源信息
	 * @param source
	 */
	Boolean updateSourceById(SourceEntity source);
	
	/**
	 * 根据id删除单个学习资源
	 * @param id
	 */
	Boolean removeSourceById(Integer id);
	
	/**
	 * 批量删除学习资源
	 * @param ids
	 */
	Boolean removeSourcesByIds(List<Integer> ids);
	
	/**
	 * 根据id查询单个学习资源
	 *
	 * @param id
	 * @return
	 */
	SourceEntity getSourceById(Integer id);
	
	
	PageUtils queryPage(Map<String, Object> params);
	
	
	/**
	 * 获取合集中的所有单个资源
	 * @param collectionId
	 * @return
	 */
	List<SourceEntity> getSourceListByCollectionId(Integer collectionId);
}

