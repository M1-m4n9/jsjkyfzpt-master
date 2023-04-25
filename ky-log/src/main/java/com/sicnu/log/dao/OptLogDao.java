package com.sicnu.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.log.dto.OptLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author LiuChuang
 * @Date 2022/11/12 17:43
 * @PackageName:com.sicnu.log.dao
 * @ClassName: OptLogDao
 * @Description: 操作日志Dao
 * @Version 1.0
 */


@Mapper
//@Repository
public interface OptLogDao extends BaseMapper<OptLogDto>
{
}
