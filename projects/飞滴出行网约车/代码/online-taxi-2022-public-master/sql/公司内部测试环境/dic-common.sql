/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017 (8.0.17)
 Source Host           : localhost:3306
 Source Schema         : dic-common

 Target Server Type    : MySQL
 Target Server Version : 80017 (8.0.17)
 File Encoding         : 65001

 Date: 22/12/2022 10:54:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dic_nation
-- ----------------------------
DROP TABLE IF EXISTS `dic_nation`;
CREATE TABLE `dic_nation`  (
  `nation_code` char(2) CHARACTER SET utf8mb4   NOT NULL COMMENT '民族编码',
  `nation_name` varchar(64) CHARACTER SET utf8mb4   NULL DEFAULT NULL COMMENT '民族名称',
  PRIMARY KEY (`nation_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

-- ----------------------------
-- Records of dic_nation
-- ----------------------------
INSERT INTO `dic_nation` VALUES ('01', '汉族');
INSERT INTO `dic_nation` VALUES ('02', '蒙古族');
INSERT INTO `dic_nation` VALUES ('03', '回族');
INSERT INTO `dic_nation` VALUES ('04', '藏族');
INSERT INTO `dic_nation` VALUES ('05', '维吾尔族');
INSERT INTO `dic_nation` VALUES ('06', '苗族');
INSERT INTO `dic_nation` VALUES ('07', '彝族');
INSERT INTO `dic_nation` VALUES ('08', '壮族');
INSERT INTO `dic_nation` VALUES ('09', '布依族');
INSERT INTO `dic_nation` VALUES ('10', '朝鲜族');
INSERT INTO `dic_nation` VALUES ('11', '满族');
INSERT INTO `dic_nation` VALUES ('12', '侗族');
INSERT INTO `dic_nation` VALUES ('13', '瑶族');
INSERT INTO `dic_nation` VALUES ('14', '白族');
INSERT INTO `dic_nation` VALUES ('15', '土家族');
INSERT INTO `dic_nation` VALUES ('16', '哈尼族');
INSERT INTO `dic_nation` VALUES ('17', '哈萨克族');
INSERT INTO `dic_nation` VALUES ('18', '傣族');
INSERT INTO `dic_nation` VALUES ('19', '黎族');
INSERT INTO `dic_nation` VALUES ('20', '僳僳族');
INSERT INTO `dic_nation` VALUES ('21', '佤族');
INSERT INTO `dic_nation` VALUES ('22', '畲族');
INSERT INTO `dic_nation` VALUES ('23', '高山族');
INSERT INTO `dic_nation` VALUES ('24', '拉祜族');
INSERT INTO `dic_nation` VALUES ('25', '水族');
INSERT INTO `dic_nation` VALUES ('26', '东乡族');
INSERT INTO `dic_nation` VALUES ('27', '纳西族');
INSERT INTO `dic_nation` VALUES ('28', '景颇族');
INSERT INTO `dic_nation` VALUES ('29', '柯尔克孜族');
INSERT INTO `dic_nation` VALUES ('30', '土族');
INSERT INTO `dic_nation` VALUES ('31', '达斡尔族');
INSERT INTO `dic_nation` VALUES ('32', '仫佬族');
INSERT INTO `dic_nation` VALUES ('33', '羌族');
INSERT INTO `dic_nation` VALUES ('34', '布朗族');
INSERT INTO `dic_nation` VALUES ('35', '撒拉族');
INSERT INTO `dic_nation` VALUES ('36', '毛难族');
INSERT INTO `dic_nation` VALUES ('37', '仡佬族');
INSERT INTO `dic_nation` VALUES ('38', '锡伯族');
INSERT INTO `dic_nation` VALUES ('39', '阿昌族');
INSERT INTO `dic_nation` VALUES ('40', '普米族');
INSERT INTO `dic_nation` VALUES ('41', '塔吉克族');
INSERT INTO `dic_nation` VALUES ('42', '怒族');
INSERT INTO `dic_nation` VALUES ('43', '乌孜别克族');
INSERT INTO `dic_nation` VALUES ('44', '俄罗斯族');
INSERT INTO `dic_nation` VALUES ('45', '鄂温克族');
INSERT INTO `dic_nation` VALUES ('46', '崩龙族');
INSERT INTO `dic_nation` VALUES ('47', '保安族');
INSERT INTO `dic_nation` VALUES ('48', '裕固族');
INSERT INTO `dic_nation` VALUES ('49', '京族');
INSERT INTO `dic_nation` VALUES ('50', '塔塔尔族');
INSERT INTO `dic_nation` VALUES ('51', '独龙族');
INSERT INTO `dic_nation` VALUES ('52', '鄂伦春族');
INSERT INTO `dic_nation` VALUES ('53', '赫哲族');
INSERT INTO `dic_nation` VALUES ('54', '门巴族');
INSERT INTO `dic_nation` VALUES ('55', '珞巴族');
INSERT INTO `dic_nation` VALUES ('56', '基诺族');
INSERT INTO `dic_nation` VALUES ('57', '其它');
INSERT INTO `dic_nation` VALUES ('58', '外国血统');

SET FOREIGN_KEY_CHECKS = 1;
