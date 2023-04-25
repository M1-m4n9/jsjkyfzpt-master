package com.sicnu.college.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.college.dao.PolicyDao;
import com.sicnu.college.entity.PolicyEntity;
import com.sicnu.college.service.PolicyService;


@Service("policyService")
public class PolicyServiceImpl extends ServiceImpl<PolicyDao, PolicyEntity> implements PolicyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PolicyEntity> page = this.page(
                new Query<PolicyEntity>().getPage(params),
                new QueryWrapper<PolicyEntity>()
        );

        return new PageUtils(page);
    }

}