/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017 (8.0.17)
 Source Host           : localhost:3306
 Source Schema         : service-passenger-user

 Target Server Type    : MySQL
 Target Server Version : 80017 (8.0.17)
 File Encoding         : 65001

 Date: 22/12/2022 10:57:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for passenger_user
-- ----------------------------
DROP TABLE IF EXISTS `passenger_user`;
CREATE TABLE `passenger_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `passenger_phone` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  `passenger_name` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  `passenger_gender` tinyint(1) NULL DEFAULT NULL COMMENT '0：未知，1：男，2：女',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '0：有效，1：失效',
  `profile_photo` varchar(128) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '头像图片地址的url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1594201071082061827 CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of passenger_user
-- ----------------------------
INSERT INTO `passenger_user` VALUES (1584355669008773122, '2022-10-24 09:26:42', '2022-10-24 09:26:42', '18178101668', '张三', 0, 0, NULL);
INSERT INTO `passenger_user` VALUES (1594201071082061826, '2022-11-20 13:28:49', '2022-11-20 13:28:49', '18178100001', '张三', 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
