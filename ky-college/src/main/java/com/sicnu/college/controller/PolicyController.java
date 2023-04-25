package com.sicnu.college.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.college.entity.PolicyEntity;
import com.sicnu.college.service.PolicyService;
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
@CrossOrigin
@RequestMapping("college/policy")
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = policyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id){
		PolicyEntity policy = policyService.getById(id);
        return R.ok().put("policy", policy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PolicyEntity policy){
		policyService.save(policy);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody PolicyEntity policy){
		policyService.updateById(policy);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		policyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
