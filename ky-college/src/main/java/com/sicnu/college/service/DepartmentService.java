package com.sicnu.college.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.college.entity.DepartmentEntity;

import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
public interface DepartmentService extends IService<DepartmentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

