package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.college.entity.DepartmentEntity;
import com.sicnu.college.service.DepartmentService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;



/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@RestController
@RequestMapping("college/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = departmentService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id){
		DepartmentEntity department = departmentService.getById(id);
        return R.ok().put("department", department);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody DepartmentEntity department){
		departmentService.save(department);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody DepartmentEntity department){
		departmentService.updateById(department);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		departmentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
