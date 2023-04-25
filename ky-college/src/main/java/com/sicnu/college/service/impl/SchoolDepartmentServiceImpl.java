package com.sicnu.college.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.college.dao.SchoolDepartmentDao;
import com.sicnu.college.entity.SchoolDepartmentEntity;
import com.sicnu.college.service.SchoolDepartmentService;


@Service("schoolDepartmentService")
public class SchoolDepartmentServiceImpl extends ServiceImpl<SchoolDepartmentDao, SchoolDepartmentEntity> implements SchoolDepartmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SchoolDepartmentEntity> page = this.page(
                new Query<SchoolDepartmentEntity>().getPage(params),
                new QueryWrapper<SchoolDepartmentEntity>()
        );

        return new PageUtils(page);
    }

}