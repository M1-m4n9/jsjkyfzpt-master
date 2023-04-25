package com.sicnu.policy;

import com.alibaba.fastjson.JSON;
import com.sicnu.common.utils.Constant;
import com.sicnu.policy.dao.PolicyDao;
import com.sicnu.policy.entity.PolicyEntity;
import com.sicnu.policy.service.PolicyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class KyPolicyApplicationTests {

    @Autowired
    PolicyDao policyDao;
    @Autowired
    PolicyService service;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void contextLoads() {

    }

    @Test
    void test01(){
        List<Integer> cur2Ids = policyDao.getCur2Ids();
        for (Integer cur2Id : cur2Ids) {
            System.out.println(cur2Id);
        }
    }

    @Test
    void test02(){
        service.getCur2MySQL();
    }

    @Test
    void test03(){
        Double aDouble = redisTemplate.opsForZSet().incrementScore(Constant.POLICY_TOP_10, String.valueOf(100), 1d);
        System.out.println(aDouble);
    }

    @Test
    void test04(){
        Double score = redisTemplate.opsForZSet().score(Constant.POLICY_TOP_10, String.valueOf(100));
        System.out.println(score);
    }

    @Test
    void test05(){
        List<PolicyEntity> top10 = service.getTop10();
        for (PolicyEntity policyEntity : top10) {
            System.out.println(policyEntity);
        }
    }

    @Test
    void test06(){
        redisTemplate.opsForZSet().add(Constant.POLICY_TOP_10,String.valueOf(1),0d);
    }
    @Test
    void test07(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        List<Integer> l = service.listIdsAndNotIn(list);
        for (Integer integer : l) {
            System.out.println(integer);
        }
    }

    @Test
    void test08(){
        Set<String> range = redisTemplate.opsForZSet().range(Constant.POLICY_TOP_10, 0, -1);
        for (String s : range) {
            System.out.println(s);
        }
    }

}
