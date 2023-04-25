package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.List;

import com.sicnu.college.vo.DepartmentCatalogVo;
import com.sicnu.college.vo.MajorCatalogVo;
import com.sicnu.college.vo.MajorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.college.entity.MajorEntity;
import com.sicnu.college.service.MajorService;
import com.sicnu.common.utils.R;

import javax.swing.plaf.basic.BasicScrollPaneUI;


/**
 *
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@RestController
@RequestMapping("college/major")
public class MajorController {
    @Autowired
    private MajorService majorService;

    /**
     * 获取专业目录下的列表信息
     * @return
     */
    @GetMapping("/major")
    public R list(@RequestParam Integer schoolId){
        List<MajorCatalogVo> majorCatalogVos= majorService.queryMajor(schoolId);
        return R.ok().put("majorCatalogVos", majorCatalogVos);
    }
    /**
     *
     */
    @GetMapping("/department")
    public R listForDepartment(Integer schoolId){
        List<DepartmentCatalogVo> departmentCatalogVos = majorService.queryDepartment(schoolId);
        return R.ok().put("departmentCatalogVos",departmentCatalogVos);
    }
    /**
     * 查看某个专业的详细信息
     */
    @GetMapping("/info")
    public R info(Integer schoolId,Integer majorId){
        MajorVo majorVo = majorService.selectMajorDetails(schoolId,majorId);
        return R.ok().put("majorVo", majorVo);
    }

    /**
     * 专业列表
     */

    @GetMapping("/list")
    public R list(){
        List<MajorEntity> list = majorService.list();
        return R.ok().put("data",list);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MajorEntity major){
		majorService.save(major);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody MajorEntity major){
		majorService.updateById(major);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		majorService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
