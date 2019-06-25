/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : financial

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-25 19:58:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_t
-- ----------------------------
DROP TABLE IF EXISTS `order_t`;
CREATE TABLE `order_t` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '订单编号',
  `outer_order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '外部订单编号',
  `chan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '渠道编号',
  `product_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '产品编号',
  `chan_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '渠道用户编号',
  `order_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '类型，APPLY:申购,REDEEM:赎回',
  `order_status` varchar(64) NOT NULL COMMENT '状态,INIT：初始化,PROCESS：处理中,SUCCESS:成功,FAIL:失败',
  `amount` decimal(15,3) NOT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of order_t
-- ----------------------------
INSERT INTO `order_t` VALUES ('1561448694340', '1561449020130', 'bbb', '1560996861552', '1561448960075', '0', '2', '3334.000', null, '2019-06-25 15:36:04', '2019-06-25 15:36:04');
INSERT INTO `order_t` VALUES ('1561448914843', '1561448673563', 'bbb', '1560997025457', '1561449025507', '0', '2', '11.000', null, '2019-06-25 15:35:13', '2019-06-25 15:35:13');
INSERT INTO `order_t` VALUES ('1561449057146', '1561448717996', 'bbb', '1560996630074', '1561448695500', '0', '2', '99999.000', null, '2019-06-25 15:36:15', '2019-06-25 15:36:15');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '产品编号',
  `name` varchar(64) NOT NULL COMMENT '产品名称',
  `threshold_amount` decimal(15,3) NOT NULL COMMENT '起投资金',
  `step_amount` decimal(15,3) NOT NULL COMMENT '投资步长',
  `lock_term` smallint(6) NOT NULL COMMENT '锁定期',
  `reward_rate` decimal(5,3) NOT NULL COMMENT '收益率',
  `status` varchar(32) NOT NULL COMMENT '状态,audinting:审核中,in_sell:销售中,locked:暂停销售,finished:已结束',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建者',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1560996630074', '健康基金', '15.000', '0.000', '0', '10.000', '0', '状如牛', '健康基金会', null, '2019-06-20 10:03:23', '2019-06-20 10:03:23');
INSERT INTO `product` VALUES ('1560996861552', '财富基金', '13.000', '0.000', '0', '29.000', '0', '一夜暴富的基金', '财富基金会', null, '2019-06-20 10:02:26', '2019-06-20 10:02:26');
INSERT INTO `product` VALUES ('1560997025457', '石油基金', '15.000', '0.000', '0', '20.000', '0', null, '石化集团', null, '2019-06-20 10:04:43', '2019-06-20 10:04:43');
INSERT INTO `product` VALUES ('aaa', 'user', '12.000', '0.000', '0', '20.000', '1', '是是是', 'user', 'user', '2019-06-19 10:01:11', '2019-06-24 09:35:06');

-- ----------------------------
-- Table structure for sign
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` varchar(20) NOT NULL,
  `private_key` varchar(1000) NOT NULL COMMENT '私钥',
  `public_key` varchar(1000) NOT NULL COMMENT '公钥',
  `auth_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签名表';

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('1561287982801', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJySGcjdyLsUWMN1kywQpyksGlHoX5J/CYZtSiAH6m7otN6CbYxMc6JP6cBHFLJqOKKro+N+f/tExp32uhnXsr28ReuIrHYDcM2I4fGB+6tAdPySo7/kYiDix3ATzLpahQvAiBKXegfb3zjjE5/7kDKNMuwCRzWVNDcj8GjgMKdPAgMBAAECgYAyYe08LuomEUES/KW0O1OfMxrxGoaOnKSTBl4g+oscz3NMWsonqH5ZT7/XaMgnNXkuF29wiEdkstf7AucV48wXMl1zuy2Rc+i5N4WVq/c7kTEuYZpf4VWm9oOZNYaGpG/WM6YzdB6RREiSf9DrMWPDdukBr3kDIQASdFQ4ucTsQQJBAOE2BWW2MNx+ZmsUArHH6tuawzhODQDi1p+FlRjZGLmXdoWRLxQRWINBSRucBu49VNdoL9jtUUt36CJ7xLVOGuECQQCx+crpyJVTG0X8bKwwB2B9jvSAvuCrIRZtUwX5p/FF881wtg2MAMtggl8dF0ARt/MUKsvKcJKB2hSD1S6siLgvAkEAqQvspzlHmeqDJDMEBsBiaO7VRKlJl3Wt1lXl4Z3rsjuf6ohdBzox72wM3V2T0jZVPc9lwvX4BKb/pwqyT8lN4QJBAIwSyT0OfVgXUxHEhhCW/b5UUOMzV6fYNsp8WWuQXjhsFfjrj+XsiiS+9xTyQ56uZEYvGI2P4/8HILZRhpaMVEkCQQClPNa4mHvWWMglx/i/919gVTF1pKKnQ7Z/rLWhnxvFeiC/m7bDTCu1nUAw8B8dQ6yncZJfxoIR59nBtyUTRfgt', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCckhnI3ci7FFjDdZMsEKcpLBpR6F+SfwmGbUogB+pu6LTegm2MTHOiT+nARxSyajiiq6Pjfn/7RMad9roZ17K9vEXriKx2A3DNiOHxgfurQHT8kqO/5GIg4sdwE8y6WoULwIgSl3oH29844xOf+5AyjTLsAkc1lTQ3I/Bo4DCnTwIDAQAB', '1000', null, '2019-06-23 18:55:02', '2019-06-23 18:55:02');
INSERT INTO `sign` VALUES ('1561298210602', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALImiVg9mO8BVW3wyVpGR49JaoAHvj94e5Frsa/l/DQX247UhYF+PCw0lehg40nw+/Qy2pZkPku6gIaoWEubT3f8B6PoI1h1q9ytFmNG0RThfWciAEX+6+DU3br9O9CsHVh0VcvN+Fzjx4z71qXrr/uyg0U4O0PI5EFaGo39/RjzAgMBAAECgYA94Iq13nc/FJyCmi6lr2FtkFdwbsAStZtd0CUgbmZiBaNaAdD5QEpQJFepJfXsGcq5b+xibiEBFp9Lpi88244HDzRZ5pYj6hTY5F3BPenJ8UfUiS+51j2n/8Hf0RKs6ysl795IGkC121Q3kVyzoyzDUTFK2ST0sGjJgXbQTHOXuQJBAOC9krkoi/aCFoU0jUVDjTlGMZYjoPzWmUnIW8mipRfJK08RoixbUyeA6AVJ2aK979HSgTiu0pcbeGdSQzt73S0CQQDK7gPKM3De1puY6WL7gIIfhOl8vm/KecvfxQtQ2xABU9C2PXis+Wzh8GXDdHYBKr6sFafpSHiljHAqxjPwIeKfAkEAgzVqkftgK50rdL2HtOaJbuTSUmues76+Y9s7626voEFsrY8H9Ymj9K5Hx76j92WYvBGALA21GU+KbhgS9ImOSQJAJEfbi5oY0u5hkTwgw0Bdg08bA390symFZrNu63zO5r3accqZuas6aUa2mvRGaL0Aq/mPNKkNUOPD5hHblVIplwJAO2IaPhaK0ViudJ98ioCQ7DFGR1JuyZL1ZCNmR4FrnOZ2CogmyWFLk3FCqeYQGyloKP2e66aJXFi0AtCoGEGKAw==', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyJolYPZjvAVVt8MlaRkePSWqAB74/eHuRa7Gv5fw0F9uO1IWBfjwsNJXoYONJ8Pv0MtqWZD5LuoCGqFhLm093/Aej6CNYdavcrRZjRtEU4X1nIgBF/uvg1N26/TvQrB1YdFXLzfhc48eM+9al66/7soNFODtDyORBWhqN/f0Y8wIDAQAB', '1', null, '2019-06-23 21:44:46', '2019-06-23 21:44:46');

-- ----------------------------
-- Table structure for verification_order
-- ----------------------------
DROP TABLE IF EXISTS `verification_order`;
CREATE TABLE `verification_order` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '订单编号',
  `order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '订单编号',
  `outer_order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '外部订单编号',
  `chan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '渠道编号',
  `product_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '产品编号',
  `chan_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '渠道用户编号',
  `order_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '类型，APPLY:申购,REDEEM:赎回',
  `amount` decimal(15,3) NOT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单验证';

-- ----------------------------
-- Records of verification_order
-- ----------------------------
INSERT INTO `verification_order` VALUES ('1561448476641', '1561449020130', '1561448694340', 'bbb', '1560996861555', '1561448960075', '0', '3334.000', null, '2019-06-25 15:37:12', '2019-06-25 16:36:02');
INSERT INTO `verification_order` VALUES ('1561448757425', '1561448717996', '1561449057146', 'bbb', '1560996630074', '1561448695500', '0', '99999.000', null, '2019-06-25 15:37:12', '2019-06-25 15:37:12');
INSERT INTO `verification_order` VALUES ('1561448975802', '1561448847521', '1561448664384', 'bbb', '1560996861552', '1561448486931', '0', '11.000', null, '2019-06-25 15:37:12', '2019-06-25 16:36:27');
