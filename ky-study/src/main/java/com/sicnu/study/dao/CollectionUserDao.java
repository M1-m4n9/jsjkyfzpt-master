package com.sicnu.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.study.entity.CollectionUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author LiuChuang
 * @Date 2022/11/28 11:11
 * @PackageName:com.sicnu.study.dao
 * @ClassName: CollectionUserDao
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface CollectionUserDao extends BaseMapper<CollectionUserEntity>
{
    @Select("select * from s_collection_user where uid=#{uid} and collection_id=#{entityId} and collection_type = #{type}")
    CollectionUserEntity selectByUTT(Integer uid,Integer entityId,Integer type);
}
