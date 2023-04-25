package com.sicnu.doubt.task;


import com.sicnu.doubt.entity.CommentEntity;
import com.sicnu.doubt.entity.PostEntity;
import com.sicnu.doubt.service.CommentService;
import com.sicnu.doubt.service.PostService;
import com.sicnu.doubt.service.TargetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.sicnu.common.utils.Constant.*;

/**
 * @author KingBob
 */
//@Configuration
//@EnableScheduling   // 1.开启定时任务
//@EnableAsync        //2.多线程
public class RedisTask {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PostService postService;
    @Autowired
    private TargetUserService targetUserService;

    @Autowired
    private CommentService commentService;

    /**
     * 帖子redis到数据库
     */
    @Async
    @Scheduled(cron = "* * 6/1 * * *")
    public void freshPostLikesCollects(){
        List<String> keys = new ArrayList<>();
        redisTemplate.execute((RedisCallback<Set<String>>) connection ->{
            ScanOptions scano = new ScanOptions.ScanOptionsBuilder().match("*:"+DOUBT_POST)
                    .count(Long.MAX_VALUE)
                    .build();
            Cursor<byte[]> cursor = connection.scan(scano);
            while(cursor.hasNext()){
                keys.add(new String(cursor.next()));
            }
            return null;
        });

        keys.stream().forEach(key->{
            Integer id=null;
            Integer likeCount = null;
            Integer postCount = null;
            Integer visitCount = null;
            Integer commentCount = null;
            PostEntity post = new PostEntity();
            String[] splits = key.split(":");
            if(splits.length<4){
                id = Integer.parseInt(splits[0]);
            }
            Object liker = redisTemplate.opsForHash().get(key, LIKE_RECORD);
            Object collectr = redisTemplate.opsForHash().get(key, COLLECT_RECORD);
            Object visitr = redisTemplate.opsForHash().get(key, VISIT_RECORD);
            Object commnetr = redisTemplate.opsForHash().get(key, COMMENT_RECORD);
            likeCount = Integer.parseInt(String.valueOf(liker));
            postCount = Integer.parseInt(String.valueOf(collectr));
            visitCount = Integer.parseInt(String.valueOf(visitr));
            commentCount = Integer.parseInt(String.valueOf(commnetr));
            post.setId(id);
            post.setLikeCount(likeCount);
            post.setPostCount(postCount);
            post.setVisitCount(visitCount);
            post.setCommentCount(commentCount);
            boolean b = postService.updateById(post);
            if(!b){

                System.out.println(
                        "/*****************************ERROR*********************************/\n"+
                        "redis->database:false(post)"+
                        "/*****************************ERROR*********************************/\n"
                        );
            }
        });

    }


    /**
     * 评论redis刷新到数据库
     */
    @Async
    @Scheduled(cron = "* * 6/1 * * *")
    public void freshCommentLikes(){
        List<String> keys = new ArrayList<>();
        redisTemplate.execute((RedisCallback<Set<String>>) connection ->{
            ScanOptions scano = new ScanOptions.ScanOptionsBuilder().match("*:"+DOUBT_COMMENT)
                    .count(Long.MAX_VALUE)
                    .build();
            Cursor<byte[]> cursor = connection.scan(scano);
            while(cursor.hasNext()){
                keys.add(new String(cursor.next()));
            }
            return null;
        });

        keys.stream().forEach(key->{
            Integer id=null;
            Integer likeCount = null;
            CommentEntity comment = new CommentEntity();
            String[] splits = key.split(":");
            if(splits.length<4){
                id = Integer.parseInt(splits[0]);
            }
            Object liker = redisTemplate.opsForHash().get(key, LIKE_RECORD);
            likeCount = Integer.parseInt(String.valueOf(liker));
            comment.setId(id);
            comment.setLikeCount(likeCount);

            boolean b = commentService.updateById(comment);
            if(!b){
                System.out.println(
                        "/*****************************ERROR*********************************/\n"+
                        "redis->database:false(comment)"+
                        "/*****************************ERROR*********************************/\n"
                );
            }
        });

    }

    /**
     * redis刷个人点赞收藏数据到数据库
     * 从6点每两小时执行
     */
    @Async
    @Scheduled(cron="* * 6/2 * * *")
    public void freshUserLikeCollect(){
        List<String> keys = new ArrayList<>();
        try {
            redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                ScanOptions scano = new ScanOptions.ScanOptionsBuilder().match(DOUBT+"*:*:[1-0]")
                        .count(Long.MAX_VALUE)
                        .build();
                Cursor<byte[]> cursor = connection.scan(scano);
                while(cursor.hasNext()){
                    keys.add(new String(cursor.next()));
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        keys.stream().forEach(key->{
            Integer uid=null;
            Integer entityId=null;
            Integer type=null;
            Integer liked=null;
            Integer collected=null;
            String[] splits = key.split(":");
            if(splits.length<5){
                uid = Integer.parseInt(splits[1]);
                entityId = Integer.parseInt(splits[2]);
                type = Integer.parseInt(splits[3]);
            }
            Object liker = redisTemplate.opsForHash().get(key, LIKE_RECORD);
            Object collectr = redisTemplate.opsForHash().get(key, COLLECT_RECORD);
            liked= Integer.parseInt(String.valueOf(liker));
            collected= Integer.parseInt(String.valueOf(collectr));
            boolean b = targetUserService.updateByR(uid, entityId, type, liked, collected);
            if(!b){
                System.out.println(
                        "/*****************************ERROR*********************************/\n"+
                        "redis->database:false(userlike and collect)"+
                        "/*****************************ERROR*********************************/\n"
                );
            }
        });
    }
}
