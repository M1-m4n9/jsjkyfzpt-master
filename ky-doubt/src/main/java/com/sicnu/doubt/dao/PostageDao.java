package com.sicnu.doubt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.doubt.entity.PostageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (DPostage)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-13 00:18:40
 */
@Mapper
public interface PostageDao extends BaseMapper<PostageEntity> {

    @Select("select taid from d_postage where pid=#{pid}")
    List<Integer> getByPidIntegers(Integer pid);
}

