//package com.sicnu.policy.quartz;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * TODO
// *
// * @author 热爱生活の李
// * @version 1.0
// * @since 2022/11/22 11:33
// */
////@Configuration
//public class QuarztConfig {
//    @Value("${quartz.policy.top10.cron}")
//    private String cron;
//
//    @Bean
//    public JobDetail topTenQuartzJobDetail(){
//        JobDetail jobDetail = JobBuilder.newJob(TopTenQuartzJob.class)
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//    @Bean
//    public Trigger topTenQuartzTrigger(){
//        CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule(cron);
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .forJob(topTenQuartzJobDetail())
//                .withSchedule(schedule)
//                .build();
//        return trigger;
//    }
//}
