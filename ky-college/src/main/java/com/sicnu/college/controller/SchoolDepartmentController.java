package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sicnu.college.entity.SchoolDepartmentEntity;
import com.sicnu.college.service.SchoolDepartmentService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;



/**
 * 
 *
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@RestController
@RequestMapping("college/schooldepartment")
public class SchoolDepartmentController {
    @Autowired
    private SchoolDepartmentService schoolDepartmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = schoolDepartmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		SchoolDepartmentEntity schoolDepartment = schoolDepartmentService.getById(id);

        return R.ok().put("schoolDepartment", schoolDepartment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SchoolDepartmentEntity schoolDepartment){
		schoolDepartmentService.save(schoolDepartment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SchoolDepartmentEntity schoolDepartment){
		schoolDepartmentService.updateById(schoolDepartment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		schoolDepartmentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
