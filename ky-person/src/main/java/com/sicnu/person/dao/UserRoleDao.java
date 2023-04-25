package com.sicnu.person.dao;

import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {

    List<RoleEntity> selectRoleByUids(int[] ids);

    List<UserRoleEntity> listUserAndRole(int[] ids);
}
