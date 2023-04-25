package com.sicnu.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.study.dao.CollectionClassificationDao;
import com.sicnu.study.entity.CollectionClassificationEntity;
import com.sicnu.study.service.CollectionClassificationService;
import org.springframework.stereotype.Service;

/**
 * @Author LiuChuang
 * @Date 2022/11/21 14:22
 * @PackageName:com.sicnu.study.service.impl
 * @ClassName: CollectionClassificationServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service("collectionClassificationService")
public class CollectionClassificationServiceImpl extends ServiceImpl<CollectionClassificationDao, CollectionClassificationEntity> implements CollectionClassificationService
{
}
