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


CREATE TABLE `reply` (
  `id` varchar(255) NOT NULL,
  `msg` varchar(255) DEFAULT NULL COMMENT '�ظ���Ϣ',
  `pet_userid` varchar(255) DEFAULT NULL COMMENT '���ͻظ����û�id',
  `reply_time` datetime DEFAULT NULL COMMENT '�ظ�ʱ��',
  `stateid` varchar(255) DEFAULT NULL COMMENT '��̬id',
  `fid` varchar(255) DEFAULT NULL COMMENT '��id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user_zan` (
  `id` varchar(255) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '�û�id',
  `zanid` bigint(20) DEFAULT NULL COMMENT 'zan',
  `type` int(20) DEFAULT NULL COMMENT '����',
  `create_time` varchar(255) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;