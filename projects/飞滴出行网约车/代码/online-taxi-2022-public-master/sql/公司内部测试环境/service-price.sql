/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017 (8.0.17)
 Source Host           : localhost:3306
 Source Schema         : service-price

 Target Server Type    : MySQL
 Target Server Version : 80017 (8.0.17)
 File Encoding         : 65001

 Date: 22/12/2022 10:57:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for price_rule
-- ----------------------------
DROP TABLE IF EXISTS `price_rule`;
CREATE TABLE `price_rule`  (
  `city_code` char(6) CHARACTER SET utf8mb4   NOT NULL COMMENT '城市代码',
  `vehicle_type` varchar(8) CHARACTER SET utf8mb4   NOT NULL COMMENT '车辆类型',
  `start_fare` double(4, 2) NULL DEFAULT NULL COMMENT '起步价',
  `start_mile` int(4) NULL DEFAULT NULL,
  `unit_price_per_mile` double(4, 2) NULL DEFAULT NULL,
  `unit_price_per_minute` double(4, 2) NULL DEFAULT NULL,
  `fare_version` int(8) NULL DEFAULT NULL,
  `fare_type` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  PRIMARY KEY (`city_code`, `vehicle_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of price_rule
-- ----------------------------
INSERT INTO `price_rule` VALUES ('110000', '1', 13.00, 3, 2.30, 2.00, 1, '110000$1');

SET FOREIGN_KEY_CHECKS = 1;
