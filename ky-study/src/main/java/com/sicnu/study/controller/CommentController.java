package com.sicnu.study.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sicnu.study.vo.CommentVo;
import com.sicnu.study.vo.DoubtCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sicnu.study.entity.CommentEntity;
import com.sicnu.study.service.CommentService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;

import static com.sicnu.common.utils.Constant.COLLECTION_ID;


/**
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
@RestController
@RequestMapping("study/comment")
public class CommentController
{
	@Autowired
	private CommentService commentService;
    
    
    /**
     * 根据用户查找评论
     *
     * @param uid
     * @return
     */
    @GetMapping("/usercomments")
    public R getByUid(@RequestParam("uid") Integer uid)
    {
        List<CommentEntity> commentEntities = commentService.getByUser(uid);

        return R.ok().put("userComments", commentEntities);
    }
	
	@GetMapping("/info")
	public R info(@RequestParam("commentId") Integer commentId)
	{
		return R.ok().put("comment", commentService.getById(commentId));
	}
    
    
	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params)
	{
		PageUtils page = commentService.queryPage(params);
		
		return R.ok().put("page", page);
	}
	
	/**
	 * 获取所有楼主评论
	 */
	@GetMapping("/all")
	public R all(@RequestParam Map<String, Object> params)
	{
		List<DoubtCommentVo> all = commentService.getOwnersComments(params);
		return R.ok().put("all", all);
	}
	
	
	/**
	 * 信息
	 */
	//    @GetMapping("/{id}")
	//    public R info(@PathVariable("id") Integer id){
	//		CommentEntity comment = commentService.getById(id);
	//
	//        return R.ok().put("comment", comment);
	//    }
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public R save(@RequestBody CommentEntity comment)
	{
		if(!commentService.saveComment(comment)) {
			R.error("服务异常");
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("/update")
	public R update(@RequestBody CommentEntity comment)
	{
		commentService.updateById(comment);
		
		return R.ok();
	}
	
	/**
	 * 批量删除评论
	 */
	@DeleteMapping("/delete")
	public R delete(@RequestBody Integer[] ids)
	{
		commentService.removeComments(ids);
		return R.ok();
	}
	

}
