package com.sicnu.doubt.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sicnu.common.utils.SensitiveUtils;
import com.sicnu.doubt.vo.PostAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.doubt.entity.PostEntity;
import com.sicnu.doubt.service.PostService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import org.springframework.web.util.HtmlUtils;



/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
@RestController
@RequestMapping("doubt/post")
public class PostController {
    @Autowired
    private PostService postService;

    SensitiveUtils sensitiveUtils =new SensitiveUtils();

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/list/top")
    public R Top(@RequestParam Map<String,Object> param){

        List<Map<Object,Object>> PostTop10 = null;
        try {
            PostTop10 = postService.listTop10(param);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统正忙");
        }

        return R.ok().put("PostTop10",PostTop10);
    }

    /**
     * 根据用户查询帖子列表
     * @param params
     * @return
     */
    @GetMapping("/uid")
    public R getAllByUser(@RequestParam Map<String, Object> params) {
        PageUtils page;
        try {
            page = postService.getAllByUid(params);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常获取失败");
        }
        return R.ok().put("PostsByUserAndType", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam Map<String,Object> params){

        PostAllVo post = null;
//        try {
            post = postService.selectById(params);
//        } catch (Exception e) {
//            return R.error().put("ableCause",e.getMessage());
//        }

        return R.ok().put("post", post);
    }

    /**
     * 保存
     */
    @PostMapping
    public R save(@RequestBody PostEntity post){
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setDigest(HtmlUtils.htmlEscape(post.getDigest()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        sensitiveUtils.init();
        post.setTitle(sensitiveUtils.filter(post.getTitle()));
        post.setDigest(sensitiveUtils.filter(post.getDigest()));
        post.setContent(sensitiveUtils.filter(post.getContent()));
        if(!postService.savePost(post)){
            return R.error("添加失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody PostEntity post){
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setDigest(HtmlUtils.htmlEscape(post.getDigest()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        sensitiveUtils.init();
        post.setTitle(sensitiveUtils.filter(post.getTitle()));
        post.setDigest(sensitiveUtils.filter(post.getDigest()));
        post.setContent(sensitiveUtils.filter(post.getContent()));
        if (!postService.updateById(post)) {
            return R.error("修改失败");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping
    public R delete(@RequestBody Integer[] ids){
        if (!postService.removePost(ids)) {
            return R.error("删除失败");
        }
        return R.ok();
    }



}
