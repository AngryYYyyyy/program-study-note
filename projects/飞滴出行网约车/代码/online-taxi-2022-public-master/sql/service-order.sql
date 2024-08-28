/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : service-order

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2022-11-16 21:36:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `passenger_id` bigint(32) DEFAULT NULL COMMENT '乘客ID',
  `passenger_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '乘客手机号',
  `driver_id` bigint(32) DEFAULT NULL COMMENT '司机ID',
  `driver_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '司机手机号',
  `car_id` bigint(32) DEFAULT NULL COMMENT '车辆Id',
  `vehicle_type` varchar(8) DEFAULT NULL,
  `address` char(6) DEFAULT NULL COMMENT '发起地行政区划代码',
  `order_time` datetime DEFAULT NULL COMMENT '订单发起时间',
  `depart_time` datetime DEFAULT NULL COMMENT '预计用车时间',
  `departure` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预计出发地点详细地址',
  `dep_longitude` varchar(16) DEFAULT NULL COMMENT '预计出发地点经度',
  `dep_latitude` varchar(16) DEFAULT NULL COMMENT '预计出发地点纬度',
  `destination` varchar(128) DEFAULT NULL COMMENT '预计目的地',
  `dest_longitude` varchar(16) DEFAULT NULL COMMENT '预计目的地经度',
  `dest_latitude` varchar(16) DEFAULT NULL COMMENT '预计目的地纬度',
  `encrypt` int(4) DEFAULT NULL COMMENT '坐标加密标识\r\n1:GCJ-02测绘局标准\r\n2:WGS84 GPS标准\r\n3:BD-09 百度标准\r\n4:CGCS2000 北斗标准\r\n0:其他',
  `fare_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '运价类型编码',
  `fare_version` int(8) DEFAULT NULL,
  `receive_order_car_longitude` varchar(16) DEFAULT NULL COMMENT '接单时车辆经度',
  `receive_order_car_latitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接单时车辆纬度',
  `receive_order_time` datetime DEFAULT NULL COMMENT '接单时间，派单成功时间',
  `license_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '机动车驾驶证号',
  `vehicle_no` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '车辆号牌',
  `to_pick_up_passenger_time` datetime DEFAULT NULL COMMENT '司机去接乘客出发时间',
  `to_pick_up_passenger_longitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '去接乘客时，司机的经度',
  `to_pick_up_passenger_latitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '去接乘客时，司机的纬度',
  `to_pick_up_passenger_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '去接乘客时，司机的地点',
  `driver_arrived_departure_time` datetime DEFAULT NULL COMMENT '司机到达上车点时间',
  `pick_up_passenger_time` datetime DEFAULT NULL COMMENT '接到乘客，乘客上车时间',
  `pick_up_passenger_longitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接到乘客，乘客上车经度',
  `pick_up_passenger_latitude` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '接到乘客，乘客上车纬度',
  `passenger_getoff_time` datetime DEFAULT NULL COMMENT '乘客下车时间',
  `passenger_getoff_longitude` varchar(16) DEFAULT NULL COMMENT '乘客下车经度',
  `passenger_getoff_latitude` varchar(16) DEFAULT NULL COMMENT '乘客下车纬度',
  `cancel_time` datetime DEFAULT NULL COMMENT '订单撤销时间',
  `cancel_operator` int(4) DEFAULT NULL COMMENT '撤销发起者：1:乘客\r\n2:驾驶员\r\n3:平台公司',
  `cancel_type_code` int(4) DEFAULT NULL COMMENT '撤销类型代码\r\n1:乘客提前撤销\r\n2:驾驶员提前撤销\r\n3:平台公司撤销\r\n4;乘客违约撤销\r\n5:驾驶员违约撤销',
  `drive_mile` bigint(16) DEFAULT NULL COMMENT '载客里程（米）',
  `drive_time` bigint(16) DEFAULT NULL COMMENT '载客时间(分)',
  `order_status` int(4) DEFAULT NULL COMMENT '订单状态1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.订单取消''',
  `price` double(10,2) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1589441348365479938 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1584370883330600961', '1584355669008773122', '18178101668', '1584359006294835202', '13910733521', '1584359540577861633', '1', '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', '116.4', '39.91', '2022-11-06 21:54:48', '机动车驾驶证号', '京N83555', '2022-11-07 22:21:18', '116.32', '36.21', '王府井', '2022-11-09 12:17:51', '2022-11-10 14:15:55', '116.33', '32.23', '2022-11-14 15:51:30', '116.21', '32.12', null, null, null, '10781', '284', '8', '40.35', '2022-11-14 14:08:37', '2022-11-14 14:08:37');
INSERT INTO `order_info` VALUES ('1584370883330600962', '1584355669008773123', '18178102668', '1584359006294835202', '13910733521', '1584359540577861633', '1', '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门1', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', '116.4', '39.91', '2022-11-11 08:56:11', '机动车驾驶证号', '京N83555', '2022-11-11 09:00:58', '116.40', '39.91', '地址', '2022-11-11 10:00:54', '2022-11-10 14:15:55', '116.40', '39.21', '2022-11-11 11:23:37', '116.40', '39.14', null, null, null, '19734', '1117', '8', null, '2022-11-14 14:08:39', '2022-11-14 14:08:39');
INSERT INTO `order_info` VALUES ('1584370883330600963', '1584355669008773124', '18178103668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门2', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:46:01', '2022-10-28 14:46:01');
INSERT INTO `order_info` VALUES ('1584370883330600964', '1584355669008773125', '18178104668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门3', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:27:59', '2022-10-28 14:27:59');
INSERT INTO `order_info` VALUES ('1584370883330600965', '1584355669008773126', '18178105668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门4', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:16:02', '2022-10-28 14:16:02');
INSERT INTO `order_info` VALUES ('1584370883330600966', '1584355669008773127', '18178106668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门5', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:16:02', '2022-10-28 14:16:02');
INSERT INTO `order_info` VALUES ('1584370883330600967', '1584355669008773128', '18178107668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门6', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:16:02', '2022-10-28 14:16:02');
INSERT INTO `order_info` VALUES ('1584370883330600968', '1584355669008773128', '18178108668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门7', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 14:16:02', '2022-10-28 14:16:02');
INSERT INTO `order_info` VALUES ('1584370883330600969', '158435566900877311', '18178109668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门8', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-11-04 12:02:29', '2022-11-04 12:02:29');
INSERT INTO `order_info` VALUES ('1584370883330600970', '1584355669008773132', '18178100668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门9', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-28 21:52:14', '2022-10-28 21:52:14');
INSERT INTO `order_info` VALUES ('1584370883330600971', '1584355669008773142', '18178111668', null, null, null, null, '110000', '2022-10-20 19:38:34', '2022-10-20 05:43:01', '天安门10', '116.40', '39.91', '鸟巢', '116.39', '39.99', '14', '110000$1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-10-27 09:11:49', '2022-10-27 09:11:49');
INSERT INTO `order_info` VALUES ('1589441348365479937', '1584355669008773122', '18178101668', null, null, null, null, '110000', '1992-12-07 19:38:34', '1991-12-06 05:43:01', 'cupidatat voluptate dolore', '116.40', '39.91', 'culpa Lorem sit', '110.365', '32.522', '14', '110000$1', '6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', null, '2022-11-07 10:15:22', '2022-11-07 10:15:22');
