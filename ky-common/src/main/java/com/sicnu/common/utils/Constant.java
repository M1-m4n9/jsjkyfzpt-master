/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.sicnu.common.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {

    public static final String POLICY_TOP_10 =  "policy_top_10";

    public static final String JWT_PAYLOAD_USER_KEY = "user";
    public static final String  POLICY_ZSET_IDS = "policy_zset_ids";
    public static final String POLICY_HASH_OBJECT = "policy_hash_object";
    public static final String POLICY_LOCK_NAME = "policy_lock_name";

    public static final String PROVINCE = "province";
    public static final String SCHOOLTYPE = "schoolType";
    public static final String SCHOOLATTRIBUTE = "schoolAttribute";


    public static final String AUTH_TOKEN_HEADER_NAME="Authorization";
    public static final String AUTH_TOKEN_HEADER_RESOURCE_START = "Ky";
    public static final String AUTH_SESSION_USER_NAME = "AuthUserInfoSession";



    public static final String CAPTCHA_CODE_KEY="captcha_code_key";

    public static final String UID = "uid";

    public static final String COLLECTION_ID = "collectionId";
    public static final String COLLECTION_TYPE = "collectionType";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String LOG_TYPE = "type";
    public static final String REQUEST_IP = "requestIp";
    public static final String HTTP_METHOD = "httpMethod";
    public static final String LOG_START_TIME = "startTime";
    public static final String LOG_END_TIME = "endTime";
    public static final String CONSUMING_TIME = "consumingTime";
    public static final String USER_AGENT = "ua";
	public static final String COURSE_COLLECTION_TOP_10_VIDEO_ZSET = "study:courseCollection:Top10:1:z";
    public static final String COURSE_COLLECTION_TOP_10_VIDEO_HASH = "study:courseCollection:Top10:1:h";
	public static final String COURSE_COLLECTION_TOP_10_FILE_ZSET = "study:courseCollection:Top10:0:z";
	public static final String COURSE_COLLECTION_TOP_10_FILE_HASH = "study:courseCollection:Top10:0:h";
	public static final String TARGET_ID = "targetId";
	
    public static final String PCTYPE="pcType";
    public static final String TYPE="type";
    
    public static final String CollectionClassificationId = "collectionClassificationId";
    public static final String LIKE_RECORD="likeRecord";
    public static final String COLLECT_RECORD="collectRecord";

    public static final String VISIT_RECORD="visitRecord";
    public static final String COMMENT_RECORD="commentRecord";

    public static final String DOUBT_COMMENT="doubt:comment";
    public static final String STUDY_COMMENT="study:comment";
    public static final String DOUBT_POST="doubt:post";
    public static final String STUDY_COLLECTION="study:collection";
    public static final String STUDY_PAPER="study:paper";
    public static final String POST_COMMENTS="post:comments";
    public static final String COLLECTION_COMMENTS="collection:comments";
    public static final String ENTITY_ID="entityId";

    public static final String DOUBT="doubt";
    public static final String STUDY="study";
    public static final String POST_TOP="post_top";
    public static final String BEGIN = "begin";
    public static final String END = "end";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String CREATE_TIME="create_time";






    public static final String CCTYPE = "ccType";
    public static final String CACHE_COURSECOLLECTION_KEY="cache:CourseCollection:";
    public static final Long CACHE_NULL_TTL=2L;
    public static final Long CACHE_COURSECLLECTION_TTL=30L+new Random().nextInt(11);
    public static final String LOCK_COURSECLLECTION_KEY="lock:CourseCollection:";
    public static final Long LOCK_COURSECLLECTION_TTL=10L;
    public static String rediskeycon(Object... params){
        StringBuilder filed= new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if(i+1<params.length){
                filed.append(String.valueOf(params[i])).append(":");
            }else {
                filed.append(String.valueOf(params[i]));
            }
        }
        return String.valueOf(filed);
    }
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */

}
