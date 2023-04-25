package com.sicnu.policy.dao;

import com.sicnu.policy.entity.PolicyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-14 12:34:12
 */
@Mapper
public interface PolicyDao extends BaseMapper<PolicyEntity> {
	List<Integer> getCur2Ids();

	List<Integer> listIdsAndNotIn(List<Integer> ids);
}
