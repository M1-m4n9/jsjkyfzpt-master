package com.sicnu.college.service.impl;

import com.sicnu.college.dao.DepartmentDao;
import com.sicnu.college.dao.MajorTypeDao;
import com.sicnu.college.dao.SchoolDao;
import com.sicnu.college.entity.DepartmentEntity;
import com.sicnu.college.entity.MajorTypeEntity;
import com.sicnu.college.entity.SchoolEntity;
import com.sicnu.college.vo.DepartmentCatalogVo;
import com.sicnu.college.vo.MajorCatalogVo;
import com.sicnu.college.vo.MajorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.college.dao.MajorDao;
import com.sicnu.college.entity.MajorEntity;
import com.sicnu.college.service.MajorService;


@Service("majorService")
public class MajorServiceImpl extends ServiceImpl<MajorDao, MajorEntity> implements MajorService {

    @Autowired
    private MajorTypeDao majorTypeDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private SchoolDao schoolDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MajorEntity> page = this.page(
                new Query<MajorEntity>().getPage(params),
                new QueryWrapper<MajorEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 通过学校id查出其下的专业目录
     * @param schooldId 学校id
     * @return
     */
    @Override
    public List<MajorCatalogVo> queryMajor(Integer schooldId) {
        QueryWrapper<MajorEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",schooldId);
        //查出来所有的major
        List<MajorEntity> majorEntityList = baseMapper.selectList(wrapper);
        if(majorEntityList == null || majorEntityList.isEmpty() || majorEntityList.size() == 0) {
            return null;
        }
        HashSet<Integer> mtids = new HashSet<>();
        //查询每个major里面的mtid代表的是那个专业类型
        majorEntityList.stream().forEach(major->mtids.add(major.getMtid()));
        List<MajorTypeEntity> majorTypeEntities = majorTypeDao.selectBatchIds(mtids);
        //封装成List<MajorCatalogVo>
        List<MajorCatalogVo> res = new ArrayList<>();

        majorTypeEntities.stream().forEach(majorTypeEntity -> {
            MajorCatalogVo majorCatalogVo = new MajorCatalogVo();
            majorCatalogVo.setMajorTypeId(majorTypeEntity.getId());
            majorCatalogVo.setMajorType(majorTypeEntity.getName());
            List<MajorVo> majorVos = majorEntityList.stream()
                    .filter(majorEntity -> majorEntity.getMtid().intValue() == majorTypeEntity.getId())
                    .map(majorEntity -> {
                        MajorVo majorVo = new MajorVo();
                        majorVo.setId(majorEntity.getId());
                        majorVo.setName(majorEntity.getName());
                        majorVo.setMajorCode(majorEntity.getMajorCode());
                        return majorVo;
                    })
                    .collect(Collectors.toList());
            majorCatalogVo.setMajorVos(majorVos);
            res.add(majorCatalogVo);
        });
        return res;
    }

    /**
     * 根据学校id和专业id查出这个专业的详细信息
     * @param schoolId 学校id
     * @param majorId 专业id
     * @return
     */
    @Override
    public MajorVo selectMajorDetails(Integer schoolId, Integer majorId) {
        MajorEntity major = baseMapper.selectOne(new QueryWrapper<MajorEntity>().eq("id", majorId).eq("sid", schoolId));
        if(major == null) {
            return null;
        }
        MajorVo majorVo = new MajorVo();
        majorVo.setId(major.getId());
        majorVo.setName(major.getName());
        majorVo.setMajorCode(major.getMajorCode());
        majorVo.setIntroduction(major.getIntroduction());
        
        majorVo.setSchoolName(schoolDao.selectById(major.getSid()).getName());
        
        MajorTypeEntity majorType = majorTypeDao.selectById(major.getMtid());
        if(majorType != null) {
            majorVo.setMajorType(majorType.getName());
        }
        DepartmentEntity department = departmentDao.selectById(major.getDid());
        if(department != null) {
            majorVo.setDepartment(department.getName());
        }
        return majorVo;
    }

    /**
     * 通过学校id查出其下的院系设置这个目录内容
     * @param schoolId 学校id
     * @return
     */
    @Override
    public List<DepartmentCatalogVo> queryDepartment(Integer schoolId) {
        //查出所有的major
        List<MajorEntity> majorEntities = baseMapper.selectList(new QueryWrapper<MajorEntity>().eq("sid", schoolId));
        if(majorEntities == null) {
            return null;
        }
        HashSet<Integer> dids = new HashSet<>();
        majorEntities.stream().forEach(majorEntity -> dids.add(majorEntity.getDid()));
        List<DepartmentEntity> departmentEntities = departmentDao.selectBatchIds(dids);
        List<DepartmentCatalogVo> res = new ArrayList<>();
        departmentEntities.stream().forEach(departmentEntity -> {
            DepartmentCatalogVo departmentCatalogVo = new DepartmentCatalogVo();
            departmentCatalogVo.setDepartmentId(departmentEntity.getId());
            departmentCatalogVo.setDepartmentName(departmentEntity.getName());
            List<MajorVo> majorVos = majorEntities.stream()
                    .filter(majorEntity -> majorEntity.getDid().intValue() == departmentEntity.getId())
                    .map(majorEntity -> {
                        MajorVo majorVo = new MajorVo();
                        majorVo.setId(majorEntity.getId());
                        majorVo.setName(majorEntity.getName());
                        majorVo.setMajorCode(majorEntity.getMajorCode());
                        return majorVo;
                    })
                    .collect(Collectors.toList());
            departmentCatalogVo.setMajorVos(majorVos);
            res.add(departmentCatalogVo);
        });
        return res;
    }

    @Override
    public List<MajorVo> getAllMajor() {
        // 所有的专业
        List<MajorEntity> majorLists = baseMapper.selectList(null);
        // 学校集合
        HashSet<Integer> sids = new HashSet<>();
        // 专业类型集合
        HashSet<Integer> mtids = new HashSet<>();
        // 系集合
        HashSet<Integer> dids = new HashSet<>();
        majorLists.stream().forEach(majorEntity -> {
            sids.add(majorEntity.getSid());
            mtids.add(majorEntity.getMtid());
            dids.add(majorEntity.getDid());
        });
        List<SchoolEntity> schools = schoolDao.selectBatchIds(sids);
        List<MajorTypeEntity> majorTypes = majorTypeDao.selectBatchIds(mtids);
        List<DepartmentEntity> departments = departmentDao.selectBatchIds(dids);

        List<MajorVo> voList = majorLists.stream().map(majorEntity -> {
            MajorVo vo = new MajorVo();
            vo.setId(majorEntity.getId());
            vo.setName(majorEntity.getName());
            vo.setMajorCode(majorEntity.getMajorCode());
            vo.setIntroduction(majorEntity.getIntroduction());

            return vo;
        }).collect(Collectors.toList());
        return voList;
    }

}