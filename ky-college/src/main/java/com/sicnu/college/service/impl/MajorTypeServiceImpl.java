package com.sicnu.college.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.college.dao.MajorTypeDao;
import com.sicnu.college.entity.MajorTypeEntity;
import com.sicnu.college.service.MajorTypeService;


@Service("majorTypeService")
public class MajorTypeServiceImpl extends ServiceImpl<MajorTypeDao, MajorTypeEntity> implements MajorTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MajorTypeEntity> page = this.page(
                new Query<MajorTypeEntity>().getPage(params),
                new QueryWrapper<MajorTypeEntity>()
        );

        return new PageUtils(page);
    }

}