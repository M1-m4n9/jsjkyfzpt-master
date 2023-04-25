/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.10:3306
 Source Schema         : ky_college

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/09/2022 23:37:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for c_department
-- ----------------------------
DROP TABLE IF EXISTS `c_department`;
CREATE TABLE `c_department`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '院的id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院的名字',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院的简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_major
-- ----------------------------
DROP TABLE IF EXISTS `c_major`;
CREATE TABLE `c_major`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '专业id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业名称',
  `major_code` char(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业代码',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业简介',
  `sid` int(10) NULL DEFAULT NULL COMMENT '学校id',
  `mtid` int(10) NULL DEFAULT NULL COMMENT '专业类型id',
  `did` int(10) NULL DEFAULT NULL COMMENT '院的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_major_type
-- ----------------------------
DROP TABLE IF EXISTS `c_major_type`;
CREATE TABLE `c_major_type`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '专业类型id',
  `name` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业类型名称',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业类型介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_policy
-- ----------------------------
DROP TABLE IF EXISTS `c_policy`;
CREATE TABLE `c_policy`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '考研政策id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '政策名字',
  `source_url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '资源地址',
  `sid` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '学校id',
  `up_date` date NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_school
-- ----------------------------
DROP TABLE IF EXISTS `c_school`;
CREATE TABLE `c_school`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '院校id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院校名称',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院校简介',
  `province` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '省份',
  `cover_url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '封面url',
  `official_website` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '官网地址',
  `college_type` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院校类型(综合类/理工类)',
  `college_attribute` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院校属性',
  `visit_count` int(10) NULL DEFAULT NULL COMMENT '访问量',
  `postgraduate_website` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '研究生官网',
  `college_announcement` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '院校公告',
  `phone` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '邮箱',
  `postal_address` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '通讯地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for c_school_department
-- ----------------------------
DROP TABLE IF EXISTS `c_school_department`;
CREATE TABLE `c_school_department`  (
  `id` int(10) NULL DEFAULT NULL COMMENT '学校和学院关系表的id',
  `sid` int(10) NULL DEFAULT NULL COMMENT '学校id',
  `did` int(10) NULL DEFAULT NULL COMMENT '学院id'
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
