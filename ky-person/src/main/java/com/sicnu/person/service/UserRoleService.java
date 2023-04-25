package com.sicnu.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.person.entity.RoleEntity;
import com.sicnu.person.entity.UserRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:36:01
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RoleEntity> selectRoleByUids(int[] ids);

    List<UserRoleEntity> listUserAndRole(int[] ids);
}

