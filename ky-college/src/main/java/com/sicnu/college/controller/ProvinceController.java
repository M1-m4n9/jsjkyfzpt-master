package com.sicnu.college.controller;

import com.sicnu.college.entity.ProvinceEntity;
import com.sicnu.college.service.ProvinceService;
import com.sicnu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/17 14:31
 */
@RestController
@RequestMapping("college/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;


    /**
     * 获取所有的省份
     */
    @GetMapping("/list")
    public R getProvinceList(){
        List<ProvinceEntity> list = provinceService.list();
        return R.ok().put("data",list);
    }
    /**
     * 删除省份
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer ids[]){
        provinceService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ProvinceEntity provinceEntity){
        provinceService.save(provinceEntity);
        return R.ok();
    }
    /**
     * 增加
     */
    @PostMapping("/add")
    public R add(@RequestBody ProvinceEntity provinceEntity){
        provinceService.save(provinceEntity);
        return R.ok();
    }
}
