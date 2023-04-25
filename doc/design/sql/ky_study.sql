/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.10:3306
 Source Schema         : ky_study

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/09/2022 23:36:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_comment
-- ----------------------------
DROP TABLE IF EXISTS `s_comment`;
CREATE TABLE `s_comment`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `uid` int(10) NULL DEFAULT NULL COMMENT '用户id(是那个用户在评论)',
  `entityType` int(10) NULL DEFAULT NULL COMMENT '评论类型(区分是评论集合,还是评论别人的评论)',
  `entityId` int(10) NULL DEFAULT NULL COMMENT '评论对象id(集合/别人评论)',
  `targetId` int(10) NULL DEFAULT NULL COMMENT '评论是那个人的评论',
  `like_count` int(10) NULL DEFAULT NULL COMMENT '点赞量',
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `type` int(1) NULL DEFAULT NULL COMMENT '是否置顶(0-不置顶 1-置顶)',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` date NULL DEFAULT NULL COMMENT '评论时间',
  `top_time` date NULL DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_course_collection
-- ----------------------------
DROP TABLE IF EXISTS `s_course_collection`;
CREATE TABLE `s_course_collection`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '视频课程合集id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频合集名称',
  `cover_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面url',
  `introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频合集介绍',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(区分是视频,还是纸质)',
  `classification` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `like_count` int(10) NULL DEFAULT NULL COMMENT '点赞总数',
  `play_count` int(10) NULL DEFAULT NULL COMMENT '播放量(视频)/下载量(纸质)',
  `share_count` int(10) NULL DEFAULT NULL COMMENT '分享量',
  `collection_count` int(10) NULL DEFAULT NULL COMMENT '收藏量',
  `comment_count` int(10) NULL DEFAULT NULL COMMENT '评论量',
  `course_duration` datetime NULL DEFAULT NULL COMMENT '课程时长',
  `uid` int(10) NULL DEFAULT NULL COMMENT '发布者id',
  `create_time` date NULL DEFAULT NULL COMMENT '发布时间',
  `score` double(10, 2) NULL DEFAULT NULL COMMENT '得分(计算热度排行)',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否展示(用来逻辑删除) 0-不展示 1-展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_source
-- ----------------------------
DROP TABLE IF EXISTS `s_source`;
CREATE TABLE `s_source`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` int(10) NULL DEFAULT NULL COMMENT '类型(区分是视频还是纸质)',
  `video_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频地址/纸质资料地址',
  `duration` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '视频时长',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序(集数排序)',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否展示(用来逻辑删除) 0-不展示 1-展示',
  `cc_id` int(10) NULL DEFAULT NULL COMMENT '合集id',
  `status` int(1) NULL DEFAULT NULL COMMENT '是否通过审核',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
