package com.sicnu.policy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sicnu.common.valid.Add;
import com.sicnu.common.valid.Update;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sicnu.policy.entity.PolicyEntity;
import com.sicnu.policy.service.PolicyService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;


/**
 * 政策服务Controller
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/9/24 20:40
 */
@RestController
@RequestMapping("policy/policy")
@CrossOrigin
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @RequestMapping("/redis")
    public R redisCache(){
        policyService.redisCache();
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = policyService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/getTop10")
    public R getTop10(){
        List<PolicyEntity> list = policyService.getTop10();
        return R.ok().put("data",list);
    }

    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam Integer id){
		PolicyEntity policy = policyService.getByPolicyById(id);
        return R.ok().put("policy", policy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(Add.class) @RequestBody PolicyEntity policy){
		policyService.savePolicy(policy);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated(Update.class) @RequestBody PolicyEntity policy){
		policyService.updateByPolicyById(policy);
        return R.ok();
    }

    /**
     * 删除
     */
    //TODO 删除时候前端传数组时候会报错
    @DeleteMapping
    public R delete(@RequestBody Long[] ids){
		policyService.removeByPolicyByIds(Arrays.asList(ids));
        return R.ok();
    }
}
