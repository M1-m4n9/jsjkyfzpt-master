package com.sicnu.doubt.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.service.TargetUserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * (TargetUser)表控制层
 *
 * @author KingBob
 * @since 2022-12-15 01:12:05
 */
@RestController
@RequestMapping("doubt/targetuser")
public class TargetUserController {
    /**
     * 服务对象
     */
    @Autowired
    private TargetUserService targetUserService;

    /**
     * 收藏列表
     *
     * @param params
     * @return 所有数据
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params)
    {
        PageUtils page = targetUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询点赞和收藏
     *
     * @param targetUserEntity
     * @return 单条数据
     */
    @GetMapping("/likclct")
    public R info(@RequestBody TargetUserEntity targetUserEntity) {
        TargetUserEntity tue = null;
        Integer uid = targetUserEntity.getUid();
        Integer entityId = targetUserEntity.getEntityId();
        Integer type = targetUserEntity.getType();
        if(entityId ==null || type==null){
            return R.error("系统异常");
        }else if(uid==null){
            return R.error("请先登录");
        }
        try {
            tue = targetUserService.selectByUTT(targetUserEntity);
            if(tue==null) {
                return R.error("参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常");
        }
        return R.ok().put("targetUser",tue);
    }


    /**
     * 点赞收藏
     *
     * @param targetUserEntity 实体对象
     * @return 修改结果
     */
    @PostMapping
    public R likeCollect(@RequestBody TargetUserEntity targetUserEntity) {
        try {
            targetUserService.likeCollect(targetUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常");
        }

        return R.ok();
    }

//    /**
//     * 删除数据
//     *
//     * @param idList 主键结合
//     * @return 删除结果
//     */
//    @DeleteMapping
//    public R delete(@RequestParam("idList") Integer[] idList) {
//        if (!targetUserService.removeByIds(Arrays.asList(idList))) {
//            return R.error("删除失败");
//        }
//        return R.ok();
//    }
}

