package com.sicnu.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.study.entity.CommentEntity;
import com.sicnu.study.vo.CommentVo;
import com.sicnu.study.vo.DoubtCommentVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:15:18
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CommentEntity> getByUser(Integer uid);

    /**
     * 批量删除评论
     * @param ids
     */
    void removeComments(Integer[] ids);

    /**
     * 获取所有楼主评论
     * @param params
     * @return
     */
    List<DoubtCommentVo> getOwnersComments(Map<String, Object> params);

    /**
     * 新增评论
     * @param commentEntity
     * @return
     */
    boolean saveComment(CommentEntity commentEntity);
}

