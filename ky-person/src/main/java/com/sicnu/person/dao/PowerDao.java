package com.sicnu.person.dao;

import com.sicnu.person.entity.PowerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.person.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
@Mapper
public interface PowerDao extends BaseMapper<PowerEntity> {

    List<RoleEntity> getRolesByUrl(String url);
}
