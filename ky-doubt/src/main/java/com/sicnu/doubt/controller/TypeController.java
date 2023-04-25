package com.sicnu.doubt.controller;

import java.util.Arrays;
import java.util.Map;

import com.sicnu.doubt.dao.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.doubt.entity.TypeEntity;
import com.sicnu.doubt.service.TypeService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;



/**
 *
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
@RestController
@RequestMapping("doubt/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = typeService.queryPage(params);

        return R.ok().put("page", page);
    }



    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        TypeEntity type = typeService.getById(id);

        return R.ok().put("type", type);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody TypeEntity type){
        if (!typeService.save(type)) {
            return R.error("添加失败");
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody TypeEntity type){
        if (!typeService.updateById(type)) {
            return R.error("修改失败");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Integer[] ids){
        if (!typeService.removeByIds(Arrays.asList(ids))) {
            return R.error("删除失败");
        }

        return R.ok();
    }

}
