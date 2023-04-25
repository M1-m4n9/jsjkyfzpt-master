package com.sicnu.doubt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicnu.common.utils.Query;
import com.sicnu.common.utils.R;
import com.sicnu.doubt.dao.CommentDao;
import com.sicnu.doubt.entity.CommentEntity;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.feign.PersonClients;
import com.sicnu.doubt.service.TargetUserService;
import com.sicnu.doubt.vo.DoubtCommentVo;
import com.sicnu.doubt.feign.entity.UserCenterVo;
import com.sicnu.doubt.service.CommentService;
import com.sicnu.doubt.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;

import static com.sicnu.common.utils.Constant.*;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private PersonClients personFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TargetUserService targetUserService;


    @Override
    public PageUtils queryPage(Map<String, Object> params)
    {
        String targetId = String.valueOf(params.get(TARGET_ID));
        Object uidl = params.get(UID);
        LambdaQueryWrapper<CommentEntity> lqw = new LambdaQueryWrapper<>();
        if (targetId != null && !targetId.isEmpty() && Integer.parseInt(targetId) != 0){lqw.eq(CommentEntity::getTargetId, Integer.parseInt(targetId));}
        lqw.eq(CommentEntity::getEntityType,1).eq(CommentEntity::getIsDeleted,0);
        IPage<CommentEntity> page = this.page(new Query<CommentEntity>().getPage(params, "create_time", true), lqw);
//		return new PageUtils(page);
        IPage<CommentVo> pageInfo = new Page<>();
        pageInfo.setPages(page.getPages());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setCurrent(page.getCurrent());
        pageInfo.setSize(page.getSize());
        List<CommentEntity> records = page.getRecords();
        List<CommentVo> newRecords = new ArrayList<>();
        for (CommentEntity record : records)
        {

            String likeCount = rediskeycon(record.getId(), DOUBT_COMMENT);
            if(Boolean.TRUE.equals(redisTemplate.hasKey(likeCount))){
                Object o = redisTemplate.opsForHash().get(likeCount, LIKE_RECORD);
                if(o!=null){
                    int likec = Integer.parseInt(String.valueOf(o));
                    record.setLikeCount(likec);
                }
            }else{
                redisTemplate.opsForHash().put(likeCount,LIKE_RECORD,record.getLikeCount().toString());
                redisTemplate.expire(likeCount,360,TimeUnit.MINUTES);
            }

            /********************************************************/
            if (uidl!=null) {
                TargetUserEntity cuEntity = new TargetUserEntity();
                cuEntity.setUid(Integer.parseInt(String.valueOf(uidl)));
                cuEntity.setEntityId(record.getId());
                cuEntity.setType(1);
                Integer cliked = targetUserService.selectByUTT(cuEntity).getLiked();
                record.setIsLike(cliked);
            }
            /********************************************************/



            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(record, commentVo);
            Integer uid = record.getUid();
            R r = personFeignService.info(uid);
            if (r.getCode() == 0)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                UserCenterVo simpleUser = objectMapper.convertValue(r.get("simpleUser"), UserCenterVo.class);
                commentVo.setUsername(simpleUser.getUsername());
                commentVo.setHeadUrl(simpleUser.getHeadUrl());
                Integer entityId = record.getEntityId();
                if (true)//entityType == 1 && Integer.parseInt(targetId) != 0 && entityId.intValue() != Integer.parseInt(targetId)
                {
                    CommentEntity byId = this.getById(entityId);
                    Integer byIdUid = byId.getUid();
                    R r1 = personFeignService.info(byIdUid);
                    if (r1.getCode() == 0)
                    {
                        UserCenterVo simpleUser1 = objectMapper.convertValue(r1.get("simpleUser"), UserCenterVo.class);
                        commentVo.setEntityUserName(simpleUser1.getUsername());
                        commentVo.setEntityUserHeadUrl(simpleUser1.getHeadUrl());
                    }
                }
            }
            newRecords.add(commentVo);
        }
        pageInfo.setRecords(newRecords);

        return new PageUtils(pageInfo);
    }

    @Override
    public List<CommentEntity> getByUser(Integer uid)
    {
        List<CommentEntity> commentEntities = baseMapper.selectByUidCommentEntities(uid);
        return commentEntities;
    }


    /**
     * 获取所有楼主评论
     * @param params
     * @return
     */
    @Override
    public List<DoubtCommentVo> getOwnersComments(Map<String,Object> params)
    {
        Integer collectionId = Integer.valueOf(String.valueOf(params.get("pId")));
        Object uidl = params.get(UID);
        LambdaQueryWrapper<CommentEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CommentEntity::getEntityType, 0).eq(CommentEntity::getEntityId, collectionId);
        lqw.eq(CommentEntity::getIsDeleted,0);
        List<CommentEntity> commentEntities = this.list(lqw);
//        List<CommentVo> commentVos = new ArrayList<>();

        /**/
        List<DoubtCommentVo> doubtCommentVos = new ArrayList<>();
        /**/

        commentEntities.stream().forEach(commentEntity -> {

            String likeCount = rediskeycon(commentEntity.getId(), DOUBT_COMMENT);
            if (Boolean.TRUE.equals(redisTemplate.hasKey(likeCount))) {
                Object o = redisTemplate.opsForHash().get(likeCount, LIKE_RECORD);
                if(o!=null){
                    int likec = Integer.parseInt(String.valueOf(o));
                    commentEntity.setLikeCount(likec);
                }
            }else{
                redisTemplate.opsForHash().put(likeCount,LIKE_RECORD,commentEntity.getLikeCount().toString());
                redisTemplate.expire(likeCount,360,TimeUnit.MINUTES);
            }

            /********************************************************/
            if (uidl!=null) {
                TargetUserEntity cuEntity = new TargetUserEntity();
                cuEntity.setUid(Integer.parseInt(String.valueOf(uidl)));
                cuEntity.setEntityId(commentEntity.getId());
                cuEntity.setType(1);
                Integer cliked = 0;
                TargetUserEntity targetUserEntity = targetUserService.selectByUTT(cuEntity);
                if(targetUserEntity!=null){
                    cliked=targetUserEntity.getLiked();
                }
                commentEntity.setIsLike(cliked);
            }else{
                commentEntity.setIsLike(0);
            }
            /********************************************************/


            CommentVo commentVo = new CommentVo();

            /**/
            DoubtCommentVo doubtCommentVo = new DoubtCommentVo();
            /**/

            BeanUtils.copyProperties(commentEntity, commentVo);
            Integer uid = commentEntity.getUid();
            R r = personFeignService.info(uid);
            if (r.getCode() == 0)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                UserCenterVo simpleUser = objectMapper.convertValue(r.get("simpleUser"), UserCenterVo.class);
                commentVo.setUsername(simpleUser.getUsername());
                commentVo.setHeadUrl(simpleUser.getHeadUrl());
            }

            /**/
            Map<String,Object> param = new HashMap<>();
            param.put("page","1");
            param.put("limit","2");
            param.put(TARGET_ID,commentVo.getId());

            if(uidl!=null){
                param.put(UID,Integer.parseInt(String.valueOf(uidl)));
            }

            PageUtils page = commentService.queryPage(param);
            doubtCommentVo.setOwnerComment(commentVo);
            doubtCommentVo.setSubComments(page);
            doubtCommentVos.add(doubtCommentVo);
            /**/

//            commentVos.add(commentVo);
        });
//        List<CommentVo> topCommentVo = commentVos.stream().filter(commentVo -> commentVo.getType() == 1).collect(Collectors.toList());
//        Collections.sort(topCommentVo, (o1, o2) -> o2.getTopTime().compareTo(o1.getTopTime()));
//        List<CommentVo> notTopCommentVo = commentVos.stream().filter(commentVo -> commentVo.getType() == 0).collect(Collectors.toList());
//        Collections.sort(notTopCommentVo, (o1, o2) -> o2.getLikeCount().compareTo(o1.getLikeCount()));

        /**/
        List<DoubtCommentVo> topdoubtCommentVo = doubtCommentVos.stream().filter(doubtCommentVo -> doubtCommentVo.getOwnerComment().getType() == 1).collect(Collectors.toList());
        Collections.sort(topdoubtCommentVo,(o1,o2)->o2.getOwnerComment().getTopTime().compareTo(o1.getOwnerComment().getTopTime()));
        List<DoubtCommentVo> notTopdoubtCommentVo = doubtCommentVos.stream().filter(doubtCommentVo -> doubtCommentVo.getOwnerComment().getType() == 0).collect(Collectors.toList());
        Collections.sort(notTopdoubtCommentVo,(o1,o2)->o2.getOwnerComment().getLikeCount().compareTo(o1.getOwnerComment().getLikeCount()));
        /**/

//        List<CommentVo> list = new ArrayList<>();
//        for (CommentVo commentVo : topCommentVo) {list.add(commentVo);}
//        for (CommentVo commentVo : notTopCommentVo) {list.add(commentVo);}

        /**/
        List<DoubtCommentVo> list2 = new ArrayList<>();
        for (DoubtCommentVo doubtCommentVo : topdoubtCommentVo) {
            list2.add(doubtCommentVo);
        }
        for (DoubtCommentVo doubtCommentVo : notTopdoubtCommentVo) {
            list2.add(doubtCommentVo);
        }
        /**/


        return list2;
    }

    @Override
    public boolean saveComment(CommentEntity commentEntity) {
        try {
        System.out.println(commentEntity.toString());
        baseMapper.insertMy(commentEntity);
            Integer id = commentEntity.getId();
            String redisc = rediskeycon(id, DOUBT_COMMENT);
            redisTemplate.opsForHash().put(redisc,LIKE_RECORD,String.valueOf(0));
            redisTemplate.expire(redisc,360, TimeUnit.MINUTES);

            if(commentEntity.getTargetId()==null){
                commentEntity.setTargetId(id);
                baseMapper.updateById(commentEntity);
                String redispost = rediskeycon(commentEntity.getEntityId(), DOUBT_POST);
                if(Boolean.TRUE.equals(redisTemplate.hasKey(redispost))){
                    redisTemplate.opsForHash().increment(redispost,COMMENT_RECORD,1);
                }

            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 批量删除评论
     * @param ids
     */
    @Override
    public void removeComments(Integer[] ids)
    {
        Arrays.asList(ids).stream().forEach(commentId -> {
            CommentEntity commentEntity = baseMapper.selectById(commentId);

            String redisco = rediskeycon(commentEntity.getEntityId(), DOUBT_POST);
            int count=-1;
            //是楼主评论则子评论全部删除
            if (commentEntity.getEntityType() == 0)
            {
                LambdaQueryWrapper<CommentEntity> lqw = new LambdaQueryWrapper<>();
                lqw.eq(CommentEntity::getTargetId, commentId);
//                List<Integer> sonIds = this.list(lqw).stream().map(CommentEntity::getEntityId).collect(Collectors.toList());
                List<CommentEntity> commentEntities = this.list(lqw);
                //this.removeByIds(sonIds);
                for (CommentEntity entity : commentEntities) {
                    entity.setIsDeleted(1);
                    count=count-1;
                    baseMapper.updateById(commentEntity);
                }
            }

            commentEntity.setIsDeleted(1);
            redisTemplate.opsForHash().increment(redisco,COMMENT_RECORD,count);
            baseMapper.updateById(commentEntity);
        });
    }


}