package com.sicnu.doubt.service.impl;

import cn.hutool.extra.ssh.JschUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.io.grpc.internal.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.sicnu.common.utils.JsonUtils;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;
import com.sicnu.doubt.dao.TargetUserDao;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.service.PostService;
import com.sicnu.doubt.service.TargetUserService;
import com.sicnu.doubt.vo.PostAllVo;
import com.sicnu.doubt.vo.PostCollectSimpleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.sicnu.common.utils.Constant.*;

/**
 * (TargetUser)表服务实现类
 *
 * @author KinBob
 * @since 2022-12-15 01:12:05
 */
@Service("targetUserService")
public class TargetUserServiceImpl extends ServiceImpl<TargetUserDao, TargetUserEntity> implements TargetUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PostService postService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String uid = String.valueOf(params.get(UID));

        LambdaQueryWrapper<TargetUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(TargetUserEntity::getUid,Integer.valueOf(uid)).eq(TargetUserEntity::getType,0).eq(TargetUserEntity::getCollected,1);
        IPage<TargetUserEntity> page = this.page(new Query<TargetUserEntity>().getPage(params,"create_time",false), lqw);
//        page.convert(u->{
//            PostCollectSimpleVo v = new PostCollectSimpleVo();
//            PostAllVo post = postService.selectById(u.getEntityId());
//            if(post!=null){
//                post.setContent(null);
//                v.setPost(post);
//            }
//
//            BeanUtils.copyProperties(u, v);
//            return v;
//        });
        page.convert(u->{
            PostAllVo v ;
            Map<String,Object> param = new HashMap<>();
            param.put("id",u.getEntityId());
            v=postService.selectById(param);
            v.setContent(null);
            return v;
        });

        return new PageUtils(page);
    }

    @Override
    public TargetUserEntity selectByUTT(TargetUserEntity targetUserEntity) {
        Integer uid = targetUserEntity.getUid();
        Integer entityId = targetUserEntity.getEntityId();
        Integer type = targetUserEntity.getType();
        TargetUserEntity tue = null;
        targetUserEntity.setLiked(0);
        targetUserEntity.setCollected(0);

        if(uid!=0 && entityId!=0){
            String rediskeycon = rediskeycon(DOUBT,uid,entityId,type);

            Object olike = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object ocollect = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            String like = String.valueOf(olike);
            String collect = String.valueOf(ocollect);

            if(olike!=null&&ocollect!=null){
                targetUserEntity.setLiked(Integer.parseInt(like));
                targetUserEntity.setCollected(Integer.parseInt(collect));
                return targetUserEntity;
            }else if(olike!=null||ocollect!=null){
                tue = baseMapper.selectByUTT(uid, entityId, type);
                if(tue==null){
                    targetUserEntity.setLiked(olike==null?1:Integer.parseInt(like));
                    targetUserEntity.setCollected(ocollect==null?1:Integer.parseInt(collect));
                    baseMapper.insert(targetUserEntity);
                    return targetUserEntity;
                }else{
                    Integer likef = olike == null ? tue.getLiked() : Integer.parseInt(like);
                    Integer collectf = ocollect == null ? tue.getCollected() : Integer.parseInt(collect);
                    redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,likef.toString());
                    redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collectf.toString());
                    redisTemplate.opsForHash().put(rediskeycon,TARGET_ID,entityId.toString());
                    redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);

                    targetUserEntity.setLiked(likef);
                    targetUserEntity.setCollected(collectf);
                    return targetUserEntity;
                }
            }else{
                tue = baseMapper.selectByUTT(uid,entityId,type);
                if(tue==null){
                    baseMapper.insert(targetUserEntity);
                    redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,String.valueOf(0));
                    redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,String.valueOf(0));
                    redisTemplate.opsForHash().put(rediskeycon,TARGET_ID,entityId.toString());
                    redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
                    return targetUserEntity;
                }else{
                    redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,tue.getLiked().toString());
                    redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,tue.getCollected().toString());
                    redisTemplate.opsForHash().put(rediskeycon,TARGET_ID,entityId.toString());
                    redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
                    return targetUserEntity;
                }

            }

        }
        return tue;
    }

    @Override
    public boolean likeCollect(TargetUserEntity targetUserEntity) {
        Integer uid = targetUserEntity.getUid();
        Integer entityId = targetUserEntity.getEntityId();
        Integer type = targetUserEntity.getType();
        Integer like = targetUserEntity.getLiked();
        Integer collecte = targetUserEntity.getCollected();
        if(entityId==null){
            return false;
        }

        //总点赞，收藏
        String redis =null;
        if(type==0){
            redis=rediskeycon(entityId,DOUBT_POST);
        }else{
            redis=rediskeycon(entityId,DOUBT_COMMENT);
        }
        if(like!=null){
            redisTemplate.opsForHash().increment(redis,LIKE_RECORD,like==0?-1:1);
        }
        if(collecte!=null){
            redisTemplate.opsForHash().increment(redis,COLLECT_RECORD,collecte==0?-1:1);
        }



        String rediskeycon = rediskeycon(DOUBT,uid,entityId,type);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(rediskeycon))) {
            Long expire = redisTemplate.getExpire(rediskeycon, TimeUnit.MINUTES);
            if(like!=null){
                redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,like.toString());
            }
            if(collecte!=null){
                redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collecte.toString());
            }
            redisTemplate.opsForHash().put(rediskeycon,ENTITY_ID,entityId.toString());
            if(241-expire<30){
                redisTemplate.expire(rediskeycon,expire-1, TimeUnit.MINUTES);
            }else{
                redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
            }
        }else{
            TargetUserEntity database = baseMapper.selectByUTT(uid, entityId, type);
            if(database!=null){
                targetUserEntity.setId(database.getId());
                baseMapper.updateById(targetUserEntity);
            }else{
                baseMapper.insert(targetUserEntity);
            }
            if(like!=null){
                redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,like.toString());
            }else{
                redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,String.valueOf(0));
            }
            if(collecte!=null){
                redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,collecte.toString());
            }else{
                redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,String.valueOf(0));
            }
            redisTemplate.opsForHash().put(rediskeycon,ENTITY_ID,entityId.toString());
            redisTemplate.expire(rediskeycon,241, TimeUnit.MINUTES);
        }

        return true;
    }

    @Override
    public boolean updateByR(Integer uid, Integer entityId, Integer type, Integer liked, Integer collected) {
        return baseMapper.updateByRedis(uid,entityId,type,liked,collected);
    }
}

