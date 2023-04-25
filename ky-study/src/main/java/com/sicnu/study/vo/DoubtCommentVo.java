package com.sicnu.study.vo;

import com.sicnu.common.utils.PageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoubtCommentVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 楼主评论
     */
    private CommentVo OwnerComment;
    /**
     * 子评论
     */
    private PageUtils SubComments;
}
