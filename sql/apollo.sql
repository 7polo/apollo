
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_data_dic
-- ----------------------------
BEGIN;
INSERT INTO `t_data_dic` VALUES ('1b6450d10f707b2eeed6e83919149289', '2019-06-16 14:29:09', b'0', '7polo github', 'https://github.com/7polo', 'friend_link');
INSERT INTO `t_data_dic` VALUES ('45d1c1cf814d4dba90819520aafe3061', '2019-07-15 12:01:22', b'0', '轮播图', 'https://github.com/7polo/resources/blob/master/apollo/carousel.png?raw=true', 'carousel');
INSERT INTO `t_data_dic` VALUES ('f644a90153e19381384154debe41d3ac', '2019-06-16 14:28:23', b'1', '轮播图', 'https://github.com/7polo/resources/blob/master/apollo/carousel.jpg?raw=true', 'carousel1');
COMMIT;

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
  `md_html` text,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
-- Records of t_sys_config
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_config` VALUES ('e028ceba96e7ac85c2e4336ffd09ee91', '2019-08-18 18:03:41', b'0', NULL, '', NULL, NULL, NULL, NULL);
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

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
  `icon` text COMMENT '图片的base64',
  `skills` varchar(500) DEFAULT NULL,
  `saying` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `name` (`username`,`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1135568155192750081', '2019-06-03 15:25:21', b'0', NULL, 'admin', '0e3ac7ddeef8307d357d87fbbd61a85e', '7cf10786-a2e9-49eb-9c26-6234ff81c172', NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
