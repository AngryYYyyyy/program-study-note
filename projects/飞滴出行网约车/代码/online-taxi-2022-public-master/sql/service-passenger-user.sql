/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : service-passenger-user

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2022-11-16 21:36:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for passenger_user
-- ----------------------------
DROP TABLE IF EXISTS `passenger_user`;
CREATE TABLE `passenger_user` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `passenger_phone` varchar(16) DEFAULT NULL,
  `passenger_name` varchar(16) DEFAULT NULL,
  `passenger_gender` tinyint(1) DEFAULT NULL COMMENT '0：未知，1：男，2：女',
  `state` tinyint(1) DEFAULT NULL COMMENT '0：有效，1：失效',
  `profile_photo` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像图片地址的url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1584355669008773123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of passenger_user
-- ----------------------------
INSERT INTO `passenger_user` VALUES ('1584355669008773122', '2022-10-24 09:26:42', '2022-10-24 09:26:42', '18178101668', '张三', '0', '0', null);
