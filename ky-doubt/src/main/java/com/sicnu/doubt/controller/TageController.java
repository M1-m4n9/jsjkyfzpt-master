package com.sicnu.doubt.controller;

import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.doubt.entity.CommentEntity;
import com.sicnu.doubt.entity.TageEntity;
import com.sicnu.doubt.service.TageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author KingBob
 */
@RestController
@RequestMapping("doubt/tage")
public class TageController {

    @Autowired
    private TageService tageService;

    /**
     * 列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page = tageService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        TageEntity tage = tageService.getById(id);

        return R.ok().put("comment", tage);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody TageEntity tage){
        tageService.save(tage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody TageEntity tage){
        tageService.updateById(tage);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Integer[] ids){
        tageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
