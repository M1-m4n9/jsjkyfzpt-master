/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.10:3306
 Source Schema         : ky_person

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/09/2022 23:37:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_power
-- ----------------------------
DROP TABLE IF EXISTS `p_power`;
CREATE TABLE `p_power`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_role
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '角色名称',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '角色介绍',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `uid` int(100) NULL DEFAULT NULL COMMENT '创建这个角色的管理员的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_role_power
-- ----------------------------
DROP TABLE IF EXISTS `p_role_power`;
CREATE TABLE `p_role_power`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '中间表id',
  `rid` int(10) NULL DEFAULT NULL COMMENT '角色id',
  `pid` int(10) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_user
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user`  (
  `id` int(10) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别(0-女，1-男)',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `region` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '地区',
  `salt` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '盐',
  `phone` char(11) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '邮箱',
  `introduction` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '个人简介',
  `status` int(1) NULL DEFAULT NULL COMMENT '是否冻结',
  `fans_count` int(10) NULL DEFAULT NULL COMMENT '粉丝数量',
  `follow_count` int(10) NULL DEFAULT NULL COMMENT '关注数量',
  `visit_count` int(10) NULL DEFAULT NULL COMMENT '访问量',
  `major_name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '专业名称',
  `school_name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '学校名字',
  `type` int(1) NULL DEFAULT NULL COMMENT '0-学生,1-老师，-1管理员',
  `is_auth` int(1) NULL DEFAULT NULL COMMENT '是否认证(0-没有 1-有)',
  `certification_materials_url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '认证材料地址',
  `head_url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '头像地址',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_user_role
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '中间表id',
  `uid` int(10) NULL DEFAULT NULL COMMENT '用户id',
  `rid` int(10) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
