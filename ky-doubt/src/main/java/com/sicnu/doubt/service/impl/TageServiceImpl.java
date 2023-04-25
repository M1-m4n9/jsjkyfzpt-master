package com.sicnu.doubt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.doubt.dao.TageDao;
import com.sicnu.doubt.entity.CommentEntity;
import com.sicnu.doubt.entity.TageEntity;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.service.TageService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author KingBob
 */
@Service("tageService")
public class TageServiceImpl extends ServiceImpl<TageDao, TageEntity> implements TageService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TageEntity> page = this.page(
                new Query<TageEntity>().getPage(params),
                new QueryWrapper<TageEntity>()
        );

        return new PageUtils(page);
    }

}
