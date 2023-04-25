package com.sicnu.college.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.college.vo.DepartmentCatalogVo;
import com.sicnu.college.vo.MajorCatalogVo;
import com.sicnu.college.vo.MajorVo;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.college.entity.MajorEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
public interface MajorService extends IService<MajorEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MajorCatalogVo> queryMajor(Integer schooldId);

    MajorVo selectMajorDetails(Integer schoolId, Integer majorId);

    List<DepartmentCatalogVo> queryDepartment(Integer schoolId);

    List<MajorVo> getAllMajor();
}

