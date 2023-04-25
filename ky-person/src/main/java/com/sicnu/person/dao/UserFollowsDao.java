package com.sicnu.person.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.person.entity.UserFollowsEntity;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

/**
 * @Author LiuChuang
 * @Date 2022/11/27 13:16
 * @PackageName:com.sicnu.person.dao
 * @ClassName: UserFollowsDao
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface UserFollowsDao extends BaseMapper<UserFollowsEntity>
{
}
