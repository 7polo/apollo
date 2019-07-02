/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Schema         : apollo

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 03/07/2019 07:25:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_catalog
-- ----------------------------
DROP TABLE IF EXISTS `t_catalog`;
CREATE TABLE `t_catalog` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dir_id` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_data_dic
-- ----------------------------
DROP TABLE IF EXISTS `t_data_dic`;
CREATE TABLE `t_data_dic` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `name` varchar(255) DEFAULT NULL,
  `value` text,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_log_record
-- ----------------------------
DROP TABLE IF EXISTS `t_log_record`;
CREATE TABLE `t_log_record` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '访问的地址',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `region` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `isp` varchar(255) DEFAULT NULL COMMENT '运营商',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_note
-- ----------------------------
DROP TABLE IF EXISTS `t_note`;
CREATE TABLE `t_note` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `dir_id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `content` text,
  `publish_dt` datetime DEFAULT NULL,
  `read_count` int(11) DEFAULT NULL,
  `good` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_seo
-- ----------------------------
DROP TABLE IF EXISTS `t_seo`;
CREATE TABLE `t_seo` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `type` varchar(255) DEFAULT NULL COMMENT '关联类型',
  `relate_id` varchar(32) NOT NULL COMMENT '关联id',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键词',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_site_map
-- ----------------------------
DROP TABLE IF EXISTS `t_site_map`;
CREATE TABLE `t_site_map` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `name` varchar(255) DEFAULT NULL COMMENT '关联类型',
  `content` text NOT NULL COMMENT '关联id',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `user_id` varchar(32) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email_pass` varchar(255) DEFAULT NULL,
  `email_port` smallint(6) DEFAULT NULL,
  `email_ssl` bit(1) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `name` (`user_id`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `name` (`name`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_tag_note
-- ----------------------------
DROP TABLE IF EXISTS `t_tag_note`;
CREATE TABLE `t_tag_note` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `tag_id` varchar(32) NOT NULL,
  `note_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `note_id` (`note_id`) USING BTREE,
  KEY `tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `uid` varchar(32) NOT NULL,
  `create_dt` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `name` (`username`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1135568155192750081', '2019-06-03 15:25:21', b'0', NULL, 'admin', 'e0ac3a60e860fbe8b13f69e3dd7f9ca4', 'f44bd641-38e7-4917-b686-dfe50b2c295f');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

