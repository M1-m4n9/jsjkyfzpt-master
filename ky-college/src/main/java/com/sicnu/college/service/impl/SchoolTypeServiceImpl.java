package com.sicnu.college.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.college.dao.SchoolDao;
import com.sicnu.college.dao.SchoolTypeDao;
import com.sicnu.college.entity.SchoolEntity;
import com.sicnu.college.entity.SchoolType;
import com.sicnu.college.service.SchoolService;
import com.sicnu.college.service.SchoolTypeService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/9 14:58
 */
@Service
public class SchoolTypeServiceImpl extends ServiceImpl<SchoolTypeDao, SchoolType> implements SchoolTypeService {
}
