package com.sicnu.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sicnu.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.study.dao.SourceDao;
import com.sicnu.study.entity.SourceEntity;
import com.sicnu.study.service.SourceService;


@Service("sourceService")
public class SourceServiceImpl extends ServiceImpl<SourceDao, SourceEntity> implements SourceService
{
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	/**
	 * 暂且放一下
	 *
	 * @param params
	 * @return
	 */
	@Override
	public PageUtils queryPage(Map<String, Object> params)
	{

		String collectionId = (String) params.get(Constant.COLLECTION_ID);
		
		QueryWrapper<SourceEntity> qw = new QueryWrapper<>();
		if (collectionId != null && !collectionId.isEmpty() && Integer.valueOf(collectionId) != 0) {qw.eq("cc_id", collectionId);}
		
		IPage<SourceEntity> page = this.page(new Query<SourceEntity>().getPage(params, "sort", true), qw);
		
		return new PageUtils(page);
	}
	
	@Override
	public List<SourceEntity> getSourceListByCollectionId(Integer collectionId)
	{
		LambdaQueryWrapper<SourceEntity> lqw = new LambdaQueryWrapper<>();
		lqw.eq(SourceEntity::getCcId, collectionId);
		lqw.orderByAsc(SourceEntity::getSort);
		List<SourceEntity> list = this.list(lqw);
		return list;
	}
	
	
	/**
	 * 新增学习资源
	 * @param source
	 * @return
	 */
	@Override
	public Boolean saveSource(SourceEntity source)
	{
		return baseMapper.insert(source) > 0;
	}
	
	/**
	 * 修改学习资源
	 * @param source
	 * @return
	 */
	@Override
	public Boolean updateSourceById(SourceEntity source)
	{
		return baseMapper.updateById(source) > 0;
	}
	
	/**
	 * 根据id删除单个学习资源
	 * @param id
	 * @return
	 */
	@Override
	public Boolean removeSourceById(Integer id)
	{
		return baseMapper.deleteById(id) > 0;
	}
	
	/**
	 * 批量删除学习资源
	 * @param ids
	 * @return
	 */
	@Override
	public Boolean removeSourcesByIds(List<Integer> ids)
	{
		//        LambdaQueryWrapper<SourceEntity> lqw = new LambdaQueryWrapper<>();
		//        lqw.in(SourceEntity::getId, ids);
		//        baseMapper.delete(lqw);
		return baseMapper.deleteBatchIds(ids) > 0;
	}
	
	
	/**
	 * 根据id获取单个学习资源
	 * @param id
	 * @return
	 */
	@Override
	public SourceEntity getSourceById(Integer id)
	{
		SourceEntity source = baseMapper.selectById(id);
		return source;
	}
	
}