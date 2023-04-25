package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.college.entity.MajorTypeEntity;
import com.sicnu.college.service.MajorTypeService;
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
@RequestMapping("college/majortype")
public class MajorTypeController {
    @Autowired
    private MajorTypeService majorTypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = majorTypeService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info")
    public R info(Integer id){
		MajorTypeEntity majorType = majorTypeService.getById(id);
        return R.ok().put("majorType", majorType);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MajorTypeEntity majorType){
		majorTypeService.save(majorType);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody MajorTypeEntity majorType){
		majorTypeService.updateById(majorType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		majorTypeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
