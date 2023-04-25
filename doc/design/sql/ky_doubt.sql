/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.10:3306
 Source Schema         : ky_doubt

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/09/2022 23:37:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for d_comment
-- ----------------------------
DROP TABLE IF EXISTS `d_comment`;
CREATE TABLE `d_comment`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `uid` int(10) NULL DEFAULT NULL COMMENT '用户id(是那个用户在评论)',
  `entityType` int(10) NULL DEFAULT NULL COMMENT '评论类型',
  `entityId` int(10) NULL DEFAULT NULL COMMENT '评论对象id',
  `targetId` int(10) NULL DEFAULT NULL COMMENT '评论是那个人的评论',
  `like_count` int(10) NULL DEFAULT NULL COMMENT '点赞量',
  `content` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '评论内容',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` date NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_post
-- ----------------------------
DROP TABLE IF EXISTS `d_post`;
CREATE TABLE `d_post`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `uid` int(10) NULL DEFAULT NULL COMMENT '帖子用户',
  `title` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL COMMENT '帖子内容',
  `status` int(1) NULL DEFAULT NULL COMMENT '审核是否通过(0-不通过 ，1 - 通过)',
  `tid` int(10) NULL DEFAULT NULL COMMENT '帖子类型',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否删除(0-删除了 1 - 没有删除)',
  `comment_count` int(10) NULL DEFAULT NULL COMMENT '评论量',
  `like_count` int(10) NULL DEFAULT NULL COMMENT '点赞量',
  `score` double(10, 2) NULL DEFAULT NULL COMMENT '得分',
  `create_time` date NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_type
-- ----------------------------
DROP TABLE IF EXISTS `d_type`;
CREATE TABLE `d_type`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '类型名称',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '类型介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
