/*
Navicat MySQL Data Transfer

Source Server         : 172.17.2.194
Source Server Version : 50720
Source Host           : 172.17.2.194:3306
Source Database       : dse

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-16 17:20:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operation_log`;
CREATE TABLE `system_operation_log` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户主键',
  `operation_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '操作时间',
  `client_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '客户端IP',
  `module` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '功能模块',
  `operation_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '操作类型',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  KEY `idx_operation_time` (`operation_time`) USING BTREE,
  KEY `idx_user_name` (`user_name`) USING BTREE,
  KEY `idx_module` (`module`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Table structure for system_operation_param
-- ----------------------------
DROP TABLE IF EXISTS `system_operation_param`;
CREATE TABLE `system_operation_param` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `log_id` varchar(50) NOT NULL COMMENT '日志主键',
  `request_param` text COMMENT '请求参数',
  `response_param` text COMMENT '响应结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作参数记录';
