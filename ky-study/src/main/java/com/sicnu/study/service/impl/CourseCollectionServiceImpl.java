package com.sicnu.study.service.impl;

import cn.hutool.core.util.StrUtil;
import com.sicnu.common.utils.Constant;
import com.sicnu.study.entity.CollectionUserEntity;
import com.sicnu.study.entity.SourceEntity;
import com.sicnu.study.service.CollectionUserService;
import com.sicnu.study.service.SourceService;
import com.sicnu.study.vo.CollectionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.study.dao.CourseCollectionDao;
import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.service.CourseCollectionService;

import static com.sicnu.common.utils.Constant.*;


@Service("courseCollectionService")
public class CourseCollectionServiceImpl extends ServiceImpl<CourseCollectionDao, CourseCollectionEntity> implements CourseCollectionService {

    @Autowired
    private SourceService sourceService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CollectionUserService collectionUserService;
    
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<CourseCollectionEntity> page = this.page(
//                new Query<CourseCollectionEntity>().getPage(params),
//                new QueryWrapper<CourseCollectionEntity>()
//        );
//
//        return new PageUtils(page);
        String uid = (String) params.get(Constant.UID);
        System.out.println(params.get(Constant.UID));
        String collectionType = (String) params.get(Constant.COLLECTION_TYPE);
        String collectionClassificationId = (String) params.get(Constant.CollectionClassificationId);
        QueryWrapper<CourseCollectionEntity> qw = new QueryWrapper<>();
        if (uid != null && !"".equals(StrUtil.trim(uid)) && Integer.valueOf(StrUtil.trim(uid)) != 0) {qw.eq("uid", Integer.valueOf(StrUtil.trim(uid)));}
        if (collectionType != null && !"".equals(StrUtil.trim(collectionType))) {qw.eq("type", Integer.valueOf(StrUtil.trim(collectionType)));}
        if (collectionClassificationId != null && !"".equals(StrUtil.trim(collectionClassificationId)) && Integer.valueOf(StrUtil.trim(collectionClassificationId)) != 0) {qw.eq("collection_classification_id", Integer.valueOf(StrUtil.trim(collectionClassificationId)));}
		
        IPage<CourseCollectionEntity> page = this.page(new Query<CourseCollectionEntity>().getPage(params, "score", false), qw);

        /*************************************KingBob*******************************************/
        page.convert(u->{
            String rediskeycon = rediskeycon(u.getId(),STUDY_COLLECTION);
            Object l = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object c = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            Object comment = redisTemplate.opsForHash().get(rediskeycon, COMMENT_RECORD);
            if(l!=null){
                int likecount = Integer.parseInt(String.valueOf(l));
                u.setLikeCount(likecount);
            }
            if(c!=null){
                int collectcount = Integer.parseInt(String.valueOf(c));
                u.setCollectionCount(collectcount);
            }
            if(comment!=null){
                int commentcount = Integer.parseInt(String.valueOf(comment));
                u.setCommentCount(commentcount);
            }
            return u;
        });
        /*************************************KingBob*******************************************/

        return new PageUtils(page);
        //        Integer uid = Integer.parseInt(String.valueOf(params.get(Constant.UID)));
//        Integer ccType = Integer.parseInt(String.valueOf(params.get(Constant.CCTYPE)));
//        LambdaQueryWrapper<CourseCollectionEntity> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(uid != null && uid != 0, CourseCollectionEntity::getUid, uid);
//        lqw.eq(ccType != null && ccType != 0, CourseCollectionEntity::getType, ccType);
//        IPage<CourseCollectionEntity> pageInfo = this.page(new Query<CourseCollectionEntity>().getPage(params, "score", false), lqw);
//        return new PageUtils(pageInfo);

    }

    /**
     * 根据合集id获取该合集
     * @param params
     * @return
     */
    @Override
    public CollectionVo getCCById(Map<String, Object> params)
    {
        Object oid = params.get("id");
        Object ouid = params.get(UID);
        Integer id = Integer.valueOf(String.valueOf(oid));
        CourseCollectionEntity courseCollection = baseMapper.selectById(id);

        /*************************************KingBob*******************************************/

        if(ouid!=null){
            CollectionUserEntity collectionUser = new CollectionUserEntity();
            collectionUser.setUid(Integer.parseInt(String.valueOf(ouid)));
            collectionUser.setCollectionId(id);
            collectionUser.setCollectionType(1);
            CollectionUserEntity userEntity = collectionUserService.selectByUTT(collectionUser);
            courseCollection.setIsLike(userEntity.getLiked());
            courseCollection.setIsCollection(userEntity.getCollected());
        }else{
            courseCollection.setIsLike(0);
            courseCollection.setIsCollection(0);
        }


        String rediskeycon = rediskeycon(courseCollection.getId(),STUDY_COLLECTION);
        if(Boolean.TRUE.equals(redisTemplate.hasKey(rediskeycon))) {
            Object l = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object c = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            Object comment = redisTemplate.opsForHash().get(rediskeycon, COMMENT_RECORD);
            if (l != null) {
                int likecount = Integer.parseInt(String.valueOf(l));
                courseCollection.setLikeCount(likecount);
            }
            if (c != null) {
                int collectcount = Integer.parseInt(String.valueOf(c));
                courseCollection.setCollectionCount(collectcount);
            }
            if(comment!=null){
                int commentcount = Integer.parseInt(String.valueOf(comment));
                courseCollection.setCommentCount(commentcount);
            }
            redisTemplate.opsForHash().increment(rediskeycon,VISIT_RECORD,1);
        }else{
            redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,courseCollection.getLikeCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,courseCollection.getCollectionCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,COMMENT_RECORD,courseCollection.getCommentCount().toString());
            redisTemplate.expire(rediskeycon,360, TimeUnit.MINUTES);
        }
        /*************************************KingBob*******************************************/



        CollectionVo collectionVo = new CollectionVo();
        BeanUtils.copyProperties(courseCollection, collectionVo);
        List<SourceEntity> sourceListByCollectionId = sourceService.getSourceListByCollectionId(id);
        collectionVo.setSourceList(sourceListByCollectionId);
        return collectionVo;
    }
    
    /**
     * 新增单个合集
     * @param courseCollection
     * @return
     */
    @Override
    public boolean saveCC(CourseCollectionEntity courseCollection)
    {
        return baseMapper.insert(courseCollection) > 0;
    }
    
    /**
     * 修改合集信息
     * @param courseCollection
     * @return
     */
    @Override
    public boolean updateCCById(CourseCollectionEntity courseCollection)
    {
        return baseMapper.updateById(courseCollection) > 0;
    }
    
    /**
     * 批量删除合集
     * @param ids
     * @return
     */
    @Override
    public boolean removeCCsByIds(Integer[] ids)
    {
        return baseMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }
    
    /**
     * 根据合集id删除单个合集
     * @param id
     * @return
     */
    @Override
    public boolean removeCCById(Integer id)
    {
        return baseMapper.deleteById(id) > 0;
    }
    
}