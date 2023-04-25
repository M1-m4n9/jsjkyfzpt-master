package com.sicnu.person.service.impl;

import com.sicnu.person.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.person.dao.UserRoleDao;
import com.sicnu.person.entity.UserRoleEntity;
import com.sicnu.person.service.UserRoleService;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRoleEntity> page = this.page(
                new Query<UserRoleEntity>().getPage(params),
                new QueryWrapper<UserRoleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<RoleEntity> selectRoleByUids(int[] ids) {
        List<RoleEntity> roles = baseMapper.selectRoleByUids(ids);
        return roles;
    }

    @Override
    public List<UserRoleEntity> listUserAndRole(int[] ids) {
        List<UserRoleEntity> ur = baseMapper.listUserAndRole(ids);
        return ur;
    }


}