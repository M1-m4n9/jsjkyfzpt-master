package com.sicnu.doubt.vo;


import com.sicnu.doubt.entity.PostEntity;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.feign.entity.UserCenterVo;
import lombok.Data;

/**
 * @author KingBob
 */
@Data
public class PostCollectSimpleVo  extends TargetUserEntity {
    private PostAllVo post;
}
