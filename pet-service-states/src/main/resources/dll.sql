CREATE TABLE `user_states` (
  `id` varchar(255) NOT NULL,
  `if_transmit_msg` tinyint(1) DEFAULT NULL,
  `imgid` varchar(4000) DEFAULT NULL,
  `msg` varchar(5000) DEFAULT NULL,
  `userid` varchar(255) NOT NULL,
  `ct` datetime DEFAULT NULL,
  `transmit_url` varchar(4000) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `state` varchar(200) DEFAULT NULL,
  `report_times` int(11) DEFAULT NULL,
  `transmit_msg` varchar(4000) DEFAULT NULL COMMENT '转发附带的评论',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `reply` (
  `id` varchar(255) NOT NULL,
  `msg` varchar(255) DEFAULT NULL COMMENT '回复信息',
  `pet_userid` varchar(255) DEFAULT NULL COMMENT '发送回复的用户id',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `stateid` varchar(255) DEFAULT NULL COMMENT '动态id',
  `fid` varchar(255) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_zan` (
  `id` varchar(255) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `zanid` bigint(20) DEFAULT NULL COMMENT 'zan',
  `type` int(20) DEFAULT NULL COMMENT '类型',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;