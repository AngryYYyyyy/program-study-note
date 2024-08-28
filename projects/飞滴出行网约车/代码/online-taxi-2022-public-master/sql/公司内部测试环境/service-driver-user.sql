/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017 (8.0.17)
 Source Host           : localhost:3306
 Source Schema         : service-driver-user

 Target Server Type    : MySQL
 Target Server Version : 80017 (8.0.17)
 File Encoding         : 65001

 Date: 22/12/2022 10:57:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `address` char(6) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆所在城市',
  `vehicle_no` varchar(8) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆号牌',
  `plate_color` char(1) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车牌颜色（1：蓝色，2：黄色，3：黑色，4：白色，5：绿色，9：其他）',
  `seats` int(3) NULL DEFAULT NULL COMMENT '核定载客位',
  `brand` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆厂牌',
  `model` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆型号',
  `vehicle_type` varchar(8) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆类型',
  `owner_name` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆所有人',
  `vehicle_color` char(2) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆颜色（1：白色，2：黑色）',
  `engine_id` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '发动机号',
  `vin` varchar(64) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  `certify_date_a` date NULL DEFAULT NULL COMMENT '车辆注册日期',
  `fue_type` char(2) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '燃料类型(1：汽油，2：柴油，3：天然气，4：液化气，5：电动，9：其他）',
  `engine_displace` varchar(8) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '发动机排量（毫升）',
  `trans_agency` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆运输证发证机构',
  `trans_area` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆经验区域',
  `trans_date_start` date NULL DEFAULT NULL COMMENT '车辆运输证有效期起',
  `trans_date_end` date NULL DEFAULT NULL COMMENT '车辆运输证有效期止',
  `certify_date_b` date NULL DEFAULT NULL COMMENT '车辆初次登记日期',
  `fix_state` char(2) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '车辆的检修状态(0：未检修，1：已检修，2：未知）',
  `next_fix_date` date NULL DEFAULT NULL COMMENT '下次年检时间',
  `check_state` char(2) CHARACTER SET utf8mb4   NULL DEFAULT '' COMMENT '年度审验状态（0：未年审，1：年审合格，2：年审不合格）',
  `fee_print_id` varchar(64) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '发票打印设备序列号',
  `gps_brand` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '卫星定位装置品牌',
  `gps_model` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '卫星型号',
  `gps_install_date` date NULL DEFAULT NULL COMMENT '卫星定位设备安装日期',
  `register_date` date NULL DEFAULT NULL COMMENT '报备日期',
  `commercial_type` int(2) NULL DEFAULT NULL COMMENT '服务类型：1：网络预约出租车，2：巡游出租车，3：私人小客车合乘',
  `fare_type` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '运价编码 关联计价规则',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '状态：0:有效，1：失效',
  `tid` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '终端Id',
  `trid` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '轨迹ID',
  `trname` varchar(32) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '轨迹名称',
  `gmt_create` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1594231277809336322 CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1584359540577861633, '110000', '京N83555', '1', 5, '大众', '迈腾', 'proident', '燃油车', '1', '18adsadf', 'vinvin', '2020-01-08', '1', '2.4t', '发证机构', '海淀区北太平街', '2020-01-02', '2020-06-05', '2016-02-09', '1', '2025-01-26', '1', '发票设备序列号', '卫星品牌', '卫星型号', '2024-02-06', '2021-08-31', 1, '110000$1', 1, '590636202', '200', NULL, '2022-10-24 23:00:19', '2022-10-24 23:00:19');
INSERT INTO `car` VALUES (1594231277809336321, '110000', '京P83123', '1', 5, '宝马', 'X3', '1', '燃油车', '1', '18adsadf', NULL, '2020-01-08', '1', '2.4t', '发证机构', '海淀区北太平街', '2020-01-02', '2020-06-05', '2016-02-09', '1', '2025-01-26', '1', '发票设备序列号', '卫星品牌', '卫星型号', '2024-02-06', '2021-08-31', 1, '110000$1', 1, '602475342', '220', '', '2022-11-20 21:55:33', '2022-11-20 21:55:33');

-- ----------------------------
-- Table structure for driver_car_binding_relationship
-- ----------------------------
DROP TABLE IF EXISTS `driver_car_binding_relationship`;
CREATE TABLE `driver_car_binding_relationship`  (
  `id` bigint(20) NOT NULL,
  `driver_id` bigint(32) NULL DEFAULT NULL,
  `car_id` bigint(32) NULL DEFAULT NULL,
  `bind_state` int(2) NULL DEFAULT NULL,
  `binding_time` datetime NULL DEFAULT NULL,
  `un_binding_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of driver_car_binding_relationship
-- ----------------------------
INSERT INTO `driver_car_binding_relationship` VALUES (1584360410912718849, 1584359006294835202, 1584359540577861633, 1, '2022-10-24 09:45:33', NULL);
INSERT INTO `driver_car_binding_relationship` VALUES (1594231566448754690, 1594206226204622849, 1594231277809336321, 1, '2022-11-20 15:29:59', NULL);

-- ----------------------------
-- Table structure for driver_user
-- ----------------------------
DROP TABLE IF EXISTS `driver_user`;
CREATE TABLE `driver_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `address` char(6) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '司机注册地行政区划代码',
  `driver_name` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '司机姓名',
  `driver_phone` varchar(16) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  `driver_gender` tinyint(2) NULL DEFAULT NULL COMMENT '1:男，2：女',
  `driver_birthday` date NULL DEFAULT NULL,
  `driver_nation` char(2) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '驾驶员民族',
  `driver_contact_address` varchar(255) CHARACTER SET utf8mb4   NULL DEFAULT NULL,
  `license_id` varchar(128) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '机动车驾驶证号',
  `get_driver_license_date` date NULL DEFAULT NULL COMMENT '初次领取驾驶证日期',
  `driver_license_on` date NULL DEFAULT NULL COMMENT '驾驶证有效期起',
  `driver_license_off` date NULL DEFAULT NULL COMMENT '驾驶证有效期止',
  `taxi_driver` tinyint(2) NULL DEFAULT NULL COMMENT '是否巡游出租汽车：1：是，0：否',
  `certificate_no` varchar(255) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '网络预约出租汽车驾驶员资格证号',
  `network_car_issue_organization` varchar(255) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '网络预约出租汽车驾驶员发证机构',
  `network_car_issue_date` date NULL DEFAULT NULL COMMENT '资格证发证日期',
  `get_network_car_proof_date` date NULL DEFAULT NULL COMMENT '初次领取资格证日期',
  `network_car_proof_on` date NULL DEFAULT NULL COMMENT '资格证有效起始日期',
  `network_car_proof_off` date NULL DEFAULT NULL COMMENT '资格证有效截止日期',
  `register_date` date NULL DEFAULT NULL COMMENT '报备日期',
  `commercial_type` tinyint(2) NULL DEFAULT NULL COMMENT '服务类型：1：网络预约出租汽车，2：巡游出租汽车，3：私人小客车合乘',
  `contract_company` varchar(255) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '驾驶员合同（协议）签署公司',
  `contract_on` date NULL DEFAULT NULL COMMENT '合同（协议）有效期起',
  `contract_off` date NULL DEFAULT NULL COMMENT '合同有效期止',
  `state` tinyint(2) NULL DEFAULT NULL COMMENT '司机状态：0：有效，1：失效',
  `gmt_create` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1594206226204622850 CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of driver_user
-- ----------------------------
INSERT INTO `driver_user` VALUES (1584359006294835202, '110000', '张司机', '13910733521', 1, '2020-01-03', '01', '通信地址', '机动车驾驶证号', '2019-01-05', '2019-01-01', '2025-01-01', 1, '网络预约出租汽车驾驶员资格证号', '网络预约出租汽车驾驶员发证机构', '2020-01-02', '2020-01-01', '2020-01-03', '2020-01-03', '2020-02-03', 1, '合约公司', '2022-01-05', '2022-01-06', 1, '2022-10-24 09:39:58', '2022-10-24 09:39:58');
INSERT INTO `driver_user` VALUES (1594206226204622849, '地址', '司机姓名', '13910730001', 1, '2020-01-03', '01', '通信地址', '机动车驾驶证号', '2019-01-05', '2019-01-01', '2025-01-01', 1, '网络预约出租汽车驾驶员资格证号', '网络预约出租汽车驾驶员发证机构', '2020-01-02', '2020-01-01', '2020-01-03', '2020-01-03', '2020-02-03', 1, '合约公司', '2022-01-05', '2022-01-06', 1, '2022-11-20 13:49:18', '2022-11-20 13:49:18');

-- ----------------------------
-- Table structure for driver_user_work_status
-- ----------------------------
DROP TABLE IF EXISTS `driver_user_work_status`;
CREATE TABLE `driver_user_work_status`  (
  `id` bigint(32) NOT NULL,
  `driver_id` bigint(32) NULL DEFAULT NULL,
  `work_status` int(4) NULL DEFAULT NULL COMMENT '收车：0；出车：1，暂停：2',
  `gmt_create` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of driver_user_work_status
-- ----------------------------
INSERT INTO `driver_user_work_status` VALUES (1584359008563953666, 1584359006294835202, 1, '2022-10-25 06:48:13', '2022-10-25 06:48:13');
INSERT INTO `driver_user_work_status` VALUES (1594206226238177281, 1594206226204622849, 1, '2022-11-20 13:49:18', '2022-11-20 13:49:18');

-- ----------------------------
-- View structure for v_ctity_driver_user_work_status
-- ----------------------------
DROP VIEW IF EXISTS `v_ctity_driver_user_work_status`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_ctity_driver_user_work_status` AS select `t1`.`id` AS `driver_id`,`t1`.`address` AS `city_code`,`t2`.`work_status` AS `work_status` from (`driver_user` `t1` left join `driver_user_work_status` `t2` on((`t1`.`id` = `t2`.`driver_id`)));

SET FOREIGN_KEY_CHECKS = 1;
