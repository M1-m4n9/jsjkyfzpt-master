package com.sicnu.doubt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.doubt.vo.PostAllVo;
import com.sicnu.doubt.entity.PostEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
public interface PostService extends IService<PostEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getAllByUid(Map<String, Object> params);

    PostAllVo selectById(Map<String,Object> params);

    boolean savePost(PostEntity post);

    boolean removePost(Integer[] ids);

    List<Map<Object,Object>> listTop10(Map<String,Object> param);
}

