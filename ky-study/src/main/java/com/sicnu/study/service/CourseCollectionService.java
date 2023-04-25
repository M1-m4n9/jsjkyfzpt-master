package com.sicnu.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.vo.CollectionVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
public interface CourseCollectionService extends IService<CourseCollectionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据合集id获取该合集
     * @param params
     * @return
     */
    CollectionVo getCCById(Map<String, Object> params);
    
    /**
     * 新增单个合集
     * @param courseCollection
     * @return
     */
    boolean saveCC(CourseCollectionEntity courseCollection);
    
    /**
     * 修改合集信息
     * @param courseCollection
     * @return
     */
    boolean updateCCById(CourseCollectionEntity courseCollection);
    
    /**
     * 批量删除合集
     * @param ids
     * @return
     */
    boolean removeCCsByIds(Integer[] ids);
    
    /**
     * 根据合集id删除单个合集
     * @param id
     * @return
     */
    boolean removeCCById(Integer id);
}

