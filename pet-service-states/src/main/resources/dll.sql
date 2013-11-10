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
  `transmit_msg` varchar(4000) DEFAULT NULL COMMENT 'ת������������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_states_reply` (
  `id` varchar(255) NOT NULL,
  `stateid` varchar(255) DEFAULT NULL COMMENT '��̬id',
  `pid` varchar(255) DEFAULT NULL COMMENT '��̬id',
  `msg` varchar(2000) DEFAULT NULL COMMENT '�ظ���Ϣ',
  `puserid` varchar(255) DEFAULT NULL COMMENT '���ͻظ����û�id',
  `userid` varchar(255) DEFAULT NULL COMMENT '���ͻظ����û�id',
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_states_audit` (
  `id` varchar(255) NOT NULL,
  `bid` varchar(255) DEFAULT NULL ,
  `biz` varchar(255) DEFAULT NULL ,
  `content` varchar(5000) DEFAULT NULL ,
  `version` int ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



