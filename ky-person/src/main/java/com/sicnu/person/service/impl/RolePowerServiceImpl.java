package com.sicnu.person.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.person.dao.RolePowerDao;
import com.sicnu.person.entity.RolePowerEntity;
import com.sicnu.person.service.RolePowerService;


@Service("rolePowerService")
public class RolePowerServiceImpl extends ServiceImpl<RolePowerDao, RolePowerEntity> implements RolePowerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePowerEntity> page = this.page(
                new Query<RolePowerEntity>().getPage(params),
                new QueryWrapper<RolePowerEntity>()
        );

        return new PageUtils(page);
    }

}