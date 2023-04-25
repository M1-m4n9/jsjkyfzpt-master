package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sicnu.college.entity.SchoolType;
import com.sicnu.college.service.SchoolTypeService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.college.entity.SchoolEntity;
import com.sicnu.college.service.SchoolService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/*
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:55:06
 */
@RestController
@RequestMapping("college/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolTypeService schoolTypeService;


    /**
     * 获取院校类型集合
     */
    @GetMapping("/schoolType/list")
    public R list(){
        List<SchoolType> list = schoolTypeService.list();
        return R.ok().put("data",list);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = schoolService.queryPage(params);
        return R.ok().put("page", page);
    }




    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(HttpServletRequest request,@RequestParam Integer id){
        System.out.println(request.getRequestURI());
        SchoolEntity school = schoolService.getById(id);
        return R.ok().put("school", school);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SchoolEntity school){
		schoolService.save(school);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody SchoolEntity school){
		schoolService.updateById(school);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		schoolService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
