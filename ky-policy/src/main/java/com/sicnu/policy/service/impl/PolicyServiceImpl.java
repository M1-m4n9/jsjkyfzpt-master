package com.sicnu.policy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.Query;

import com.sicnu.policy.dao.PolicyDao;
import com.sicnu.policy.entity.PolicyEntity;
import com.sicnu.policy.service.PolicyService;


@Service("policyService")
public class PolicyServiceImpl extends ServiceImpl<PolicyDao, PolicyEntity> implements PolicyService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    //TODO 如何保证分页从查询一定是查询的是那一页如歌redis没有缓存所有数据
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String province = (String) params.get("province");
        QueryWrapper<PolicyEntity> wrapper = new QueryWrapper<>();
        if(province != null && province != "" && !province.isEmpty()){
            wrapper.eq("province",province);
        }
        IPage<PolicyEntity> page = this.page(
                new Query<PolicyEntity>().getPage(params),
               wrapper);

        return new PageUtils(page);
        

//        IPage<PolicyEntity> iPage = new Query<PolicyEntity>().getPage(params, "up_time", false);
//        //获取到当前是第几页
//        long current = iPage.getCurrent();
//        //获取每一页的大小
//        long size = iPage.getSize();
//        //算出需要多少范围的数据
//        long start = (current - 1) * size+1;
//        long end = start+size-1;
//        Long s = redisTemplate.opsForZSet().size(Constant.POLICY_ZSET_IDS);
//        Set<String> range = redisTemplate.opsForZSet().reverseRange(Constant.POLICY_ZSET_IDS, start-1, end-1);
//        List<Object> list = new ArrayList<>();
//        range.stream().forEach(list::add);
//        List<Object> data = redisTemplate.opsForHash().multiGet(Constant.POLICY_HASH_OBJECT, list);
//        List<PolicyEntity> list1 = new ArrayList<>();
//        iPage.setTotal(s);
//        data.stream().forEach((d)->{
//            PolicyEntity policyEntity = JSON.parseObject((String) d, PolicyEntity.class);
//            list1.add(policyEntity);
//        });
//        iPage.setRecords(list1);
//        return new PageUtils(iPage);
    }

    @Override
    public PolicyEntity getByPolicyById(Integer id) {
        addVisited(id);
        Object o = redisTemplate.opsForHash().get(Constant.POLICY_HASH_OBJECT, Integer.toString(id));
        PolicyEntity policy = JSON.parseObject((String) o, PolicyEntity.class);
        if(policy == null){
            String uuid = UUID.randomUUID().toString();
            Boolean lock = redisTemplate.opsForValue().setIfAbsent(Constant.POLICY_LOCK_NAME+id,uuid,2,TimeUnit.SECONDS);
            if(lock){
                //避免缓存击穿
                policy = baseMapper.selectById(id);
                // 就算数据库没到也要缓存 避免缓存穿透 TODO 但是没有过期时间就会一直保存找个办法解决
                if(policy != null){
                    redisTemplate.opsForZSet().add(Constant.POLICY_ZSET_IDS,Integer.toString(id),(double) (policy.getUpTime().getTime()-1664042704));
                    redisTemplate.opsForHash().put(Constant.POLICY_HASH_OBJECT,Integer.toString(id),JSON.toJSONString(policy));
                }else{
                    redisTemplate.opsForZSet().add(Constant.POLICY_ZSET_IDS,Integer.toString(id),1d);
                    redisTemplate.opsForHash().put(Constant.POLICY_HASH_OBJECT,Integer.toString(id),JSON.toJSONString(new PolicyEntity()));
                }
                // 使用lua脚本
                String s = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                //解锁
                redisTemplate.execute(new DefaultRedisScript<Integer>(s,Integer.class),Arrays.asList(Constant.POLICY_LOCK_NAME+id),uuid);
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getByPolicyById(id);
            }
        }
        return policy;
    }

    @Override
    public void redisCache() {
        List<PolicyEntity> list = baseMapper.selectList(null);
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        Map<String,String> map = new HashMap<>();
        list.stream().forEach((record)->{
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<String>(Integer.toString(record.getId()),(double) (record.getUpTime().getTime()-1664042704));
            set.add(tuple);
            map.put(Integer.toString(record.getId()),JSON.toJSONString(record));
        });
        redisTemplate.opsForZSet().add(Constant.POLICY_ZSET_IDS,set);
        redisTemplate.opsForHash().putAll(Constant.POLICY_HASH_OBJECT,map);
    }

    @Override
    public void savePolicy(PolicyEntity policy) {
        baseMapper.insert(policy);
        redisTemplate.opsForZSet().add(Constant.POLICY_ZSET_IDS,Integer.toString(policy.getId()),(double) (policy.getUpTime().getTime()-1664042704));
        redisTemplate.opsForHash().put(Constant.POLICY_HASH_OBJECT,Integer.toString(policy.getId()),JSON.toJSONString(policy));
    }

    @Override
    public void updateByPolicyById(PolicyEntity policy) {
        baseMapper.updateById(policy);
        redisTemplate.opsForZSet().add(Constant.POLICY_ZSET_IDS,Integer.toString(policy.getId()),(double) (policy.getUpTime().getTime()-1664042704));
        redisTemplate.opsForHash().put(Constant.POLICY_HASH_OBJECT,Integer.toString(policy.getId()),JSON.toJSONString(policy));
    }

    //TODO 有可能会出错
    @Override
    public void removeByPolicyByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
        redisTemplate.opsForZSet().remove(Constant.POLICY_ZSET_IDS,asList.toString());//????
        redisTemplate.opsForHash().delete(Constant.POLICY_HASH_OBJECT,asList.toString());//????
    }

    @Override
    public List<PolicyEntity> getTop10() {
        Set<String> ids = redisTemplate.opsForZSet().reverseRange(Constant.POLICY_TOP_10, 0, 9);
        List<Object> list = new ArrayList<>();
        ids.stream().forEach(list::add);
        List<Object> multiGet = redisTemplate.opsForHash().multiGet(Constant.POLICY_HASH_OBJECT, list);
        List<PolicyEntity> res = new ArrayList<>();
        multiGet.stream().forEach((d)->{
           PolicyEntity policyEntity = JSON.parseObject((String) d, PolicyEntity.class);
           res.add(policyEntity);
       });
        return res;
    }

    /**
     * 获取最近的20条数据并放入Redis
     * @return
     */
    public void getCur2MySQL(){
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        List<Integer> cur2Ids = baseMapper.getCur2Ids();
        cur2Ids.stream().forEach(e->{
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<String>(String.valueOf(e),0d);
            set.add(tuple);
        });
        redisTemplate.opsForZSet().removeRange(Constant.POLICY_TOP_10,0,-1);
        redisTemplate.opsForZSet().add(Constant.POLICY_TOP_10,set);
    }

    @Override
    public List<Integer> listIdsAndNotIn(List<Integer> ids) {
        return baseMapper.listIdsAndNotIn(ids);
    }

    /**
     * 有访问就加一
     * @param id
     */
    public void addVisited(Integer id){
        if(redisTemplate.opsForZSet().score(Constant.POLICY_TOP_10, String.valueOf(id)) != null){
            redisTemplate.opsForZSet().incrementScore(Constant.POLICY_TOP_10,String.valueOf(id),1d);
        }
    }
}