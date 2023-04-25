package com.sicnu.college.service.impl;

import com.sicnu.common.utils.Constant;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.college.dao.SchoolDao;
import com.sicnu.college.entity.SchoolEntity;
import com.sicnu.college.service.SchoolService;


@Service("schoolService")
public class SchoolServiceImpl extends ServiceImpl<SchoolDao, SchoolEntity> implements SchoolService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String provinceId = (String)params.get(Constant.PROVINCE);
        String schoolType = (String) params.get(Constant.SCHOOLTYPE);
        String schoolAttribute = (String) params.get(Constant.SCHOOLATTRIBUTE);
        String key = (String)params.get("key");
        QueryWrapper<SchoolEntity> wrapper = new QueryWrapper<>();
        // 为空或者0都代表没有这个条件
        if(provinceId != null && !provinceId.isEmpty() && Integer.valueOf(provinceId )!= 0)wrapper.eq("province",provinceId);
        if(schoolType != null && !schoolType.isEmpty() && Integer.valueOf(schoolType) != 0)wrapper.eq("college_type",schoolType);
        if(schoolAttribute!=null && !schoolAttribute.isEmpty() && Integer.valueOf(schoolAttribute) != 0)wrapper.eq("college_attribute",schoolAttribute    );
        if (key!=null&&!key.isEmpty())wrapper.like("name",key);
        IPage<SchoolEntity> page = this.page(new Query<SchoolEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}