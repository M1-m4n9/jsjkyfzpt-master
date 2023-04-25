package com.sicnu.doubt.controller;





import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.doubt.entity.PostageEntity;
import com.sicnu.doubt.service.PostageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * (DPostage)表控制层
 *
 * @author makejava
 * @since 2022-12-13 00:18:40
 */
@RestController
@RequestMapping("doubt/postage")
public class PostageController{
    /**
     * 服务对象
     */
    @Autowired
    private PostageService postageService;

    /**
     * 列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = postageService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        PostageEntity postage = postageService.getById(id);

        return R.ok().put("postage", postage);
    }


    /**
     * 新增数据
     *
     * @param postageEntity 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R save(@RequestBody PostageEntity postageEntity) {
        if (!postageService.save(postageEntity)) {
            return R.error("保存失败");
        }
        return R.ok();
    }

    /**
     * 修改数据
     *
     * @param postageEntity 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody PostageEntity postageEntity) {
        if (!postageService.updateById(postageEntity)) {
            return R.error("修改失败");
        }
        return R.ok();
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") Integer[] ids) {
        if (postageService.removeByIds(Arrays.asList(ids))) {
            return R.error("修改失败");
        }
        return R.ok();
    }
}

