//package com.sicnu.policy.quartz;
//
//import com.sicnu.common.utils.Constant;
//import com.sicnu.policy.service.PolicyService;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.DefaultTypedTuple;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * TODO
// *
// * @author 热爱生活の李
// * @version 1.0
// * @since 2022/11/22 11:32
// */
//
////@Component
//public class TopTenQuartzJob extends QuartzJobBean {
//
////    @Autowired
//    StringRedisTemplate redisTemplate;
////    @Autowired
//    PolicyService policyService;
//    @Override
//    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        redisTemplate.opsForZSet().removeRange(Constant.POLICY_TOP_10,0,4);
//        Set<String> ids = redisTemplate.opsForZSet().range(Constant.POLICY_TOP_10, 0, -1);
//        List<Integer> list = ids.stream().map(e -> Integer.valueOf(e)).collect(Collectors.toList());
//        List<Integer> id = policyService.listIdsAndNotIn(list);
//        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
//        id.stream().forEach(e->{
//            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<String>(String.valueOf(e),0d);
//            set.add(tuple);
//        });
//        redisTemplate.opsForZSet().add(Constant.POLICY_TOP_10,set);
//    }
//}
