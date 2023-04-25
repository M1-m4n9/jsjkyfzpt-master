package com.sicnu.doubt.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sicnu.common.utils.R;
import com.sicnu.doubt.dao.PostageDao;
import com.sicnu.doubt.dao.TageDao;
import com.sicnu.doubt.dao.TypeDao;
import com.sicnu.doubt.entity.TargetUserEntity;
import com.sicnu.doubt.service.TargetUserService;
import com.sicnu.doubt.vo.FollowsVo;
import com.sicnu.doubt.vo.PostAllVo;
import com.sicnu.doubt.entity.TageEntity;
import com.sicnu.doubt.entity.TypeEntity;
import com.sicnu.doubt.feign.PersonClients;
import com.sicnu.doubt.feign.entity.UserCenterVo;
import com.sicnu.doubt.vo.UserFollowsVo;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.doubt.dao.PostDao;
import com.sicnu.doubt.entity.PostEntity;
import com.sicnu.doubt.service.PostService;
import org.springframework.web.util.HtmlUtils;

import static com.sicnu.common.utils.Constant.*;
import static com.sicnu.common.utils.Constant.ASC;


@Service("postService")
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TageDao tageDao;

    @Autowired
    private PostageDao postageDao;

    @Autowired
    private PersonClients personClients;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PostService postService;

    @Autowired
    private TargetUserService targetUserService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer uid=null;
        String sidx=null;
        Boolean asc=null;
        Integer type=null;
        if (params.containsKey(UID)) {
            if(params.get(UID)!="") {
                uid = Integer.parseInt(String.valueOf(params.get(UID)));
            }
        }
        if(params.containsKey(ORDER_FIELD)){
            int i = Integer.parseInt(String.valueOf(params.get(ORDER_FIELD)));
            if(i==1){
                sidx="create_time";
            }else if(i==2){
                sidx="like_count";
            }else if(i==3){
                sidx="post_count";
            }else if(i==4){
                sidx="visit_count";
            }else if(i==5){
                sidx="score";
            }
        }
        if(params.containsKey(ASC)){
            asc = Objects.equals(String.valueOf(params.get(ASC)), "true");
        }
        if(params.containsKey(PCTYPE)){
            type = Integer.parseInt(String.valueOf(params.get(PCTYPE)));
        }
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params,sidx, Boolean.TRUE.equals(asc)),
                new LambdaQueryWrapper<PostEntity>().eq(type!=null,PostEntity::getTid,type)
                        .eq(uid!=null&&uid!=0, PostEntity::getUid,uid)
                        .eq(PostEntity::getIsDeleted,0)
        );
        page.convert(u->{
            //redis
            String rediskeycon = rediskeycon(u.getId(), DOUBT_POST);
            Object l = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object c = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            Object visit = redisTemplate.opsForHash().get(rediskeycon, VISIT_RECORD);
            Object comment = redisTemplate.opsForHash().get(rediskeycon, COMMENT_RECORD);
            if(l!=null){
                int likecount = Integer.parseInt(String.valueOf(l));
                u.setLikeCount(likecount);
            }
            if(c!=null){
                int collectcount = Integer.parseInt(String.valueOf(c));
                u.setPostCount(collectcount);
            }
            if(visit!=null){
                int visitcount = Integer.parseInt(String.valueOf(visit));
                u.setVisitCount(visitcount);
            }
            if(comment!=null){
                int commentcount = Integer.parseInt(String.valueOf(comment));
                u.setCommentCount(commentcount);
            }


            PostAllVo v = new PostAllVo();
            TypeEntity byId = typeDao.selectById(u.getTid());
            if (byId!=null){
                        //设置属性
                    v.setTypename(byId.getName());
                    v.setTypeintroduction(byId.getIntroduction());
                    }
            R info = null;
            try {
                info = personClients.info(u.getUid());
                ObjectMapper objectMapper = new ObjectMapper();
                UserCenterVo user = objectMapper.convertValue(info.get("simpleUser"), UserCenterVo.class);
                if(user!=null){
                    v.setUser(user);
                }
            } catch (Exception e) {
                v.setUser(null);
            }
            u.setContent(null);
            List<String> tages = new ArrayList<>();
            List<Integer> taids = postageDao.getByPidIntegers(u.getId());
            for (Integer taid : taids) {
                TageEntity tage = tageDao.selectById(taid);
                tages.add(tage.getName());
            }
            v.setTages(tages);
            BeanUtils.copyProperties(u, v);//拷贝
                    return v;
                });
        return new PageUtils(page);
    }

    @Override
    public PageUtils getAllByUid(Map<String, Object> params) {
        Integer uid=null;
        String sidx=null;
        Boolean asc=null;
        Integer type=null;
        if(!params.containsKey(UID)){
            return null;
        }
        if(params.get(UID)!="") {
            uid = Integer.parseInt(String.valueOf(params.get(UID)));
        }
        if(params.containsKey(ORDER_FIELD)){
            int i = Integer.parseInt(String.valueOf(params.get(ORDER_FIELD)));
            if(i==1){
                sidx="create_time";
            }else if(i==2){
                sidx="like_count";
            }else if(i==3){
                sidx="post_count";
            }else if(i==4){
                sidx="visit_count";
            }else if(i==5){
                sidx="score";
            }
        }
        if(params.containsKey(ASC)){
            asc = Objects.equals(String.valueOf(params.get(ASC)), "true");
        }
        if(params.containsKey(TYPE)){
            type= Integer.valueOf(String.valueOf(params.get(TYPE)));
        }
        IPage<PostEntity> page=this.page(
                new Query<PostEntity>().getPage(params,sidx, Boolean.TRUE.equals(asc)),
                new LambdaQueryWrapper<PostEntity>().eq(uid!=null&&uid!=0,PostEntity::getUid,uid)
                        .eq(type!=null,PostEntity::getTid,type)
                        .eq(PostEntity::getIsDeleted,0)
        );
        page.convert(u->{
            u.setContent(null);


            String rediskeycon = rediskeycon(u.getId(), DOUBT_POST);
            Object l = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object c = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            Object visit = redisTemplate.opsForHash().get(rediskeycon, VISIT_RECORD);
            Object comment = redisTemplate.opsForHash().get(rediskeycon, COMMENT_RECORD);
            if (l != null) {
                int likecount = Integer.parseInt(String.valueOf(l));
                u.setLikeCount(likecount);
            }
            if (c != null) {
                int collectcount = Integer.parseInt(String.valueOf(c));
                u.setPostCount(collectcount);
            }
            if (visit != null) {
                int visitcount = Integer.parseInt(String.valueOf(visit));
                u.setVisitCount(visitcount);
            }
            if(comment!=null){
                int commentcount = Integer.parseInt(String.valueOf(comment));
                u.setCommentCount(commentcount);
            }
            return u;
        });
        return new PageUtils(page);
    }

    @Override
    public PostAllVo selectById(Map<String,Object> params) {

        Object ouid = params.get(UID);
        Integer uid = null;
        if(ouid!=null){
            if(!("undefined".equals(String.valueOf(ouid)))){
                uid = Integer.valueOf(String.valueOf(ouid));
            }
        }

        Object oid = params.get("id");
        Integer id = Integer.valueOf(String.valueOf(oid));
        PostEntity post = baseMapper.selectById(id);
        if(post.getIsDeleted()==1){
            PostAllVo pp=new PostAllVo();
            pp.setIsDeleted(1);
            return pp;
        }
        post.setTitle(HtmlUtils.htmlUnescape(post.getTitle()));
        post.setDigest(HtmlUtils.htmlUnescape(post.getDigest()));
        post.setContent(HtmlUtils.htmlUnescape(post.getContent()));

        if(uid!=null){
            TargetUserEntity TUE = new TargetUserEntity();
            TUE.setUid(uid);
            TUE.setEntityId(id);
            TUE.setType(0);

            TargetUserEntity targetUserEntity = targetUserService.selectByUTT(TUE);
            if(targetUserEntity!=null){
                post.setIsLike(targetUserEntity.getLiked());
                post.setIsPost(targetUserEntity.getCollected());
            }else{
                post.setIsLike(0);
                post.setIsPost(0);
            }
        }else{
            post.setIsLike(0);
            post.setIsPost(0);
        }


        String rediskeycon = rediskeycon(post.getId(), DOUBT_POST);
        if(Boolean.TRUE.equals(redisTemplate.hasKey(rediskeycon))) {
            Object l = redisTemplate.opsForHash().get(rediskeycon, LIKE_RECORD);
            Object c = redisTemplate.opsForHash().get(rediskeycon, COLLECT_RECORD);
            Object visit = redisTemplate.opsForHash().get(rediskeycon, VISIT_RECORD);
            Object comment = redisTemplate.opsForHash().get(rediskeycon, COMMENT_RECORD);
            if (l != null) {
                int likecount = Integer.parseInt(String.valueOf(l));
                post.setLikeCount(likecount);
            }
            if (c != null) {
                int collectcount = Integer.parseInt(String.valueOf(c));
                post.setPostCount(collectcount);
            }
            if (visit != null) {
                int visitcount = Integer.parseInt(String.valueOf(visit));
                post.setVisitCount(visitcount+1);
            }
            if(comment!=null){
                int commentcount = Integer.parseInt(String.valueOf(comment));
                post.setCommentCount(commentcount);
            }
            redisTemplate.opsForHash().increment(rediskeycon,VISIT_RECORD,1);
        }else{
            redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,post.getLikeCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,post.getPostCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,VISIT_RECORD,post.getVisitCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,COMMENT_RECORD,post.getCommentCount().toString());
            redisTemplate.opsForHash().put(rediskeycon,TITLE,post.getTitle());
            redisTemplate.opsForHash().put(rediskeycon,URL,post.getUrl());
            redisTemplate.opsForHash().put(rediskeycon,CREATE_TIME,DateUtil.format(post.getCreateTime(),"yyyy-MM-dd"));
            redisTemplate.expire(rediskeycon,360,TimeUnit.MINUTES);
        }


        R info = null;
        R follow = null;
        PostAllVo postAll =new PostAllVo();
        Map<String,Object> paramf = new HashMap<>();
        paramf.put("uid",uid);
        try {
            info = personClients.info(post.getUid());
            if(uid!=null){
                follow = personClients.follow(paramf);
            }

        } catch (Exception e) {
            throw new RuntimeException("服务不可用");
        }

        if(info.get("simpleUser")!=null) {
            Object simpleUser = info.get("simpleUser");
            ObjectMapper mapper=new ObjectMapper();
            UserCenterVo user = null;
            user = mapper.convertValue(simpleUser, UserCenterVo.class);
            postAll.setUser(user);

            if (uid!=null) {
                Object ofollow = follow.get("page");
                if(ofollow!=null){
                        FollowsVo followsVo = null;
                        followsVo=mapper.convertValue(ofollow, FollowsVo.class);
                    if(followsVo.getRecords()!=null){
                            followsVo.getRecords().stream().forEach(ufollowsVo->{
                                if(ufollowsVo.getUid().equals(post.getUid())){
                                    Integer status = ufollowsVo.getStatus();
//                                    if(status ==0){
                                        postAll.setIsFollow(1);
//                                    }else{
//                                        postAll.setIsFollow(2);
//                                    }

                                }
                            });
                        }
                    }
            }

        }else{
            postAll.setUser(null);
            postAll.setIsFollow(0);
        }
        TypeEntity type = typeDao.selectById(post.getTid());
        List<String> tages = new ArrayList<>();
        List<Integer> taids = postageDao.getByPidIntegers(id);
        for (Integer taid : taids) {
            TageEntity tage = tageDao.selectById(taid);
            tages.add(tage.getName());
        }
        postAll.setTages(tages);
        BeanUtils.copyProperties(post,postAll);

        postAll.setTypename(type.getName());
        postAll.setTypeintroduction(type.getIntroduction());


        /**/
        redisTemplate.opsForHash().put(rediskeycon,USER_NAME,postAll.getUser().getUsername());
        redisTemplate.expire(rediskeycon,360,TimeUnit.MINUTES);
        /**/
        return postAll;
    }

    @Override
    public boolean savePost(PostEntity post) {
        if (baseMapper.insert(post)<1) {
            return false;
        }
        try {
            Integer id = post.getId();
            String redispost = rediskeycon(id, DOUBT_POST);
            redisTemplate.opsForHash().put(redispost,LIKE_RECORD,String.valueOf(0));
            redisTemplate.opsForHash().put(redispost,COLLECT_RECORD,String.valueOf(0));
            redisTemplate.opsForHash().put(redispost,VISIT_RECORD,String.valueOf(0));
            redisTemplate.opsForHash().put(redispost,COMMENT_RECORD,String.valueOf(0));

            /**/
            redisTemplate.opsForHash().put(redispost,TITLE,post.getTitle());
            R info = personClients.info(post.getUid());
            Object simpleUser = info.get("simpleUser");
            ObjectMapper mapper=new ObjectMapper();
            UserCenterVo user = null;
            user = mapper.convertValue(simpleUser, UserCenterVo.class);
            String username = user.getUsername();
            redisTemplate.opsForHash().put(redispost,USER_NAME,username);
            redisTemplate.opsForHash().put(redispost,URL,post.getUrl());
            redisTemplate.opsForHash().put(redispost,CREATE_TIME,post.getCreateTime());
            /**/
            redisTemplate.expire(redispost,360, TimeUnit.MINUTES);
            redisTemplate.opsForZSet().add(POST_TOP,id.toString(),0);
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    @Override
    public boolean removePost(Integer[] ids) {

        try {
            Arrays.asList(ids).stream().forEach(postId->{
                PostEntity post = baseMapper.selectById(postId);
                if(post!=null){
                    post.setIsDeleted(1);
                    baseMapper.updateById(post);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Map<Object,Object>> listTop10(Map<String,Object> param) {

        List<Map<Object,Object>> postTop = new ArrayList<>();
        Integer uid = null;
        Integer begin = null;
        Integer end = null;
        if(param.containsKey(UID)){
            uid = Integer.parseInt(String.valueOf(param.get(UID)));
        }
        if(param.containsKey(BEGIN)){
            begin =Integer.parseInt(String.valueOf(param.get(BEGIN)));
        }else{
            begin = 0;
        }
        if(param.containsKey(END)){
            end =Integer.parseInt(String.valueOf(param.get(END)));
        }else{
            end=begin+10;
        }
        if(begin>end){
            return null;
        }


        Set<String> spostTop = redisTemplate.opsForZSet().reverseRange(POST_TOP, begin, end);
        if(spostTop!=null && spostTop.size()!=0) {
            List<String> lpostTop = new ArrayList<>(spostTop);

            for (String s : lpostTop) {
                String rediskeycon = rediskeycon(Integer.parseInt(s), DOUBT_POST);
                Map<Object, Object> entries=null;
                if(Boolean.TRUE.equals(redisTemplate.hasKey(rediskeycon))){
                    entries = redisTemplate.opsForHash().entries(rediskeycon);
                }else{
                    Map<String,Object> params = new HashMap<>();
                    params.put("id",Integer.valueOf(s));
                    PostAllVo posto = postService.selectById(params);

                    redisTemplate.opsForHash().put(rediskeycon,LIKE_RECORD,posto.getLikeCount().toString());
                    redisTemplate.opsForHash().put(rediskeycon,COLLECT_RECORD,posto.getPostCount().toString());
                    redisTemplate.opsForHash().put(rediskeycon,VISIT_RECORD,posto.getVisitCount().toString());
                    redisTemplate.opsForHash().put(rediskeycon,COMMENT_RECORD,posto.getCommentCount().toString());
                    redisTemplate.opsForHash().put(rediskeycon,TITLE,posto.getTitle());
                    redisTemplate.opsForHash().put(rediskeycon,USER_NAME,posto.getUser().getUsername());
                    redisTemplate.opsForHash().put(rediskeycon,URL,posto.getUrl());
                    redisTemplate.opsForHash().put(rediskeycon,CREATE_TIME, DateUtil.format(posto.getCreateTime(),"yyyy-MM-dd"));
                    redisTemplate.expire(rediskeycon,360, TimeUnit.MINUTES);

                    entries = redisTemplate.opsForHash().entries(rediskeycon);
                }
                entries.remove(LIKE_RECORD);
                entries.remove(COLLECT_RECORD);
                entries.remove(VISIT_RECORD);
                entries.remove(COMMENT_RECORD);
                entries.remove(USER_NAME);
                postTop.add(entries);
            }
        }else{
            LambdaQueryWrapper<PostEntity> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PostEntity::getIsDeleted,0).orderByDesc(PostEntity::getScore);
            List<PostEntity> postEntities = baseMapper.selectList(lqw);
            postEntities.stream().forEach(post->{
                redisTemplate.opsForZSet().add(POST_TOP,post.getId().toString(),post.getScore());
            });
            List<Map<Object, Object>> maps = postService.listTop10(param);
            postTop=maps;
        }
        return postTop;
    }

}