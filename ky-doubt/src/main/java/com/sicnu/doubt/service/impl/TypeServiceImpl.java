package com.sicnu.doubt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.doubt.dao.TypeDao;
import com.sicnu.doubt.entity.TypeEntity;
import com.sicnu.doubt.service.TypeService;


@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements TypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TypeEntity> page = this.page(
                new Query<TypeEntity>().getPage(params),
                new LambdaQueryWrapper<TypeEntity>()
        );

        return new PageUtils(page);
    }

}