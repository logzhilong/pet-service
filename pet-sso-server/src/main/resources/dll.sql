-- ----------------------------
-- Table structure for `chat_server`
-- ----------------------------
DROP TABLE IF EXISTS `chat_server`;
CREATE TABLE `chat_server` (
  `id` varchar(60) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for `pet_info`
-- ----------------------------
DROP TABLE IF EXISTS `pet_info`;
CREATE TABLE `pet_info` (
  `id` varchar(60) NOT NULL ,
  `gender` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `type` bigint(20) NOT NULL,
  `userid` varchar(60) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `trait` varchar(255) DEFAULT NULL,
  `birthdate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for `pet_user`
-- ----------------------------
DROP TABLE IF EXISTS `pet_user`;
CREATE TABLE `pet_user` (
  `id` varchar(60) NOT NULL ,
  `hobby` varchar(255) DEFAULT NULL COMMENT '用户爱好',
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `birthdate` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `if_fraudulent` varchar(255) DEFAULT '0',
  `device_token` varchar(255) DEFAULT NULL,
  `background_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for `pet_version`
-- ----------------------------
DROP TABLE IF EXISTS `pet_version`;
CREATE TABLE `pet_version` (
  `id` varchar(60) NOT NULL ,
  `version` bigint(20) DEFAULT NULL,
  `pet_version` varchar(255) DEFAULT NULL COMMENT ' 版本号',
  `create_date` datetime NULL DEFAULT NULL,
  `phone_type` varchar(255) DEFAULT NULL,
  `iosurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;







-- ----------------------------
-- Table structure for `user_friendship`
-- ----------------------------
DROP TABLE IF EXISTS `user_friendship`;
CREATE TABLE `user_friendship` (
  `id` varchar(60) NOT NULL,
  `a_id` varchar(60) NOT NULL,
  `b_id` varchar(60) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `verified` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `aliasa` varchar(255) DEFAULT NULL,
  `aliasb` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;