/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.10:3306
 Source Schema         : ky_policy

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/09/2022 23:37:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for p_policy
-- ----------------------------
DROP TABLE IF EXISTS `p_policy`;
CREATE TABLE `p_policy`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '政策法规id',
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '政策法规文件名字',
  `province` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '省',
  `source_url` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '资源地址',
  `up_time` date NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
