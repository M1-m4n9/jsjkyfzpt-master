package com.sicnu.doubt.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sicnu.doubt.entity.TargetUserEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (DTargetUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-15 01:12:05
 */
@Mapper
public interface TargetUserDao extends BaseMapper<TargetUserEntity> {

    /**
     * 获取点赞收藏关系
     * @param uid
     * @param entityId
     * @param type
     * @return
     */
    @Select("select * from d_target_user where uid=#{uid} and entity_id=#{entityId} and type = #{type}")
    TargetUserEntity selectByUTT(Integer uid,Integer entityId,Integer type);

    /**
     * redis更新数据库
     * @param uid
     * @param entityId
     * @param type
     * @param liked
     * @param collected
     * @return
     */
    @Update("update d_target_user set liked=#{liked},collected=#{collected} where uid=#{uid} and entity_id=#{entityId} and type=#{type}")
    boolean updateByRedis(Integer uid, Integer entityId,Integer type,Integer liked,Integer collected);
}

