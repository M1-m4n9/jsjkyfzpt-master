package com.sicnu.doubt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.doubt.dao.PostageDao;
import com.sicnu.doubt.entity.CommentEntity;
import com.sicnu.doubt.entity.PostageEntity;
import com.sicnu.doubt.service.PostageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * (DPostage)表服务实现类
 *
 * @author KingBob
 * @since 2022-12-13 00:18:40
 */
@Service("PostageService")
public class PostageServiceImpl extends ServiceImpl<PostageDao, PostageEntity> implements PostageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostageEntity> page = this.page(
                new Query<PostageEntity>().getPage(params),
                new QueryWrapper<PostageEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Integer> selectByPid(Integer pid) {
        List<Integer> taids = baseMapper.getByPidIntegers(pid);

        return taids;
    }
}

