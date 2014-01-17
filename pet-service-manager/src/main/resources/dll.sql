create database pet_manager;

--后台管理的用户角色DLL
CREATE TABLE `mgr_user` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(500) DEFAULT NULL COMMENT '用户名',
  `password` varchar(500) DEFAULT NULL COMMENT '密码',
  `ct` timestamp ,
  `et` timestamp ,
  `cb` varchar(255) DEFAULT NULL COMMENT '创建人',
  `eb` varchar(255) DEFAULT NULL COMMENT '修改人',
  `enable` boolean ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `mgr_role` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `code` varchar(500) DEFAULT NULL COMMENT '编码,需要用角色校验时,要使用编码',
  `name` varchar(500) DEFAULT NULL COMMENT '角色名称',
  `desct` varchar(2000) DEFAULT NULL COMMENT '角色概述',
  `ct` datetime,
  `et` datetime,
  `enable` boolean,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `mgr_user_role_rel` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--password=abc123
insert into mgr_user (id,name,password,cb,eb,ct,et,enable) values('root','root','5NKa8wXQN8BeBdUQOjQqquZPjvAy0A5BJyvQIQkd6/I=','root','root','2013-10-10','2013-10-10',true);
insert into mgr_role (id,code,name,desct,ct,et,enable) value ('root','root','管理员','超级用户','2013-10-10','2013-10-10',true);
insert into mgr_user_role_rel (id,user_id,role_id)values('root','root','root');


CREATE TABLE `mgr_trust_user` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(500) DEFAULT NULL COMMENT '被管理的用户',
  `nroot_id` varchar(500) DEFAULT NULL COMMENT '管理用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mgr_service_dict` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `alias` varchar(500) DEFAULT NULL COMMENT '别名',
  `service` varchar(500) DEFAULT NULL COMMENT '服务注册名',
  `method` varchar(500) DEFAULT NULL COMMENT '方法名',
  `ct` datetime,
  `et` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',  
  `eb` varchar(200) DEFAULT NULL COMMENT 'edit by',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mgr_channel_dict` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `alias` varchar(500) DEFAULT NULL COMMENT '别名',
  `code` varchar(500) DEFAULT NULL COMMENT '渠道号',
  `ct` datetime,
  `et` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',  
  `eb` varchar(200) DEFAULT NULL COMMENT 'edit by',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `mgr_published_apk` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `file_id` varchar(500) DEFAULT NULL COMMENT '文件ID',
  `version` varchar(500) DEFAULT NULL COMMENT '版本',
  `channel` varchar(500) DEFAULT NULL COMMENT '渠道号',
  `ct` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mgr_push` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(2000) DEFAULT NULL COMMENT '标题',
  `src` varchar(500) DEFAULT NULL COMMENT '类型：帖子、百科、经验',
  `state` varchar(500) DEFAULT NULL COMMENT '状态：未推送、已推送、等待',
  `expir` varchar(200) DEFAULT NULL COMMENT '到期时间，时间戳',
  `ct` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',
  `et` datetime,
  `eb` varchar(200) DEFAULT NULL,    
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mgr_timer_task` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(2000) DEFAULT NULL COMMENT '标题',
  `src` varchar(500) DEFAULT NULL COMMENT '类型：帖子、推送',
  `state` varchar(500) DEFAULT NULL COMMENT '状态：新增、完成、取消',
  `at` datetime,
  `ct` datetime,
  `et` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',
  `eb` varchar(200) DEFAULT NULL,    
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



+--------------+---------------+------+-----+---------+-------+
| Field        | Type          | Null | Key | Default | Extra |
+--------------+---------------+------+-----+---------+-------+
| ID           | varchar(64)   | NO   | PRI |         |       |
| FORUM_ID     | varchar(64)   | YES  |     | NULL    |       |
| USER_ID      | varchar(64)   | YES  |     | NULL    |       |
| NAME         | varchar(1000) | YES  |     | NULL    |       |
| CONTENT      | text          | YES  |     | NULL    |       |
| CLIENT_COUNT | bigint(20)    | YES  |     | NULL    |       |
| IS_EUTE      | tinyint(1)    | YES  |     | NULL    |       |
| IS_TOP       | tinyint(1)    | YES  |     | NULL    |       |
| IS_DEL       | tinyint(1)    | YES  |     | NULL    |       |
| STATE        | varchar(200)  | YES  |     | NULL    |       |
| TYPE         | varchar(200)  | YES  |     | NULL    |       |
| CT           | datetime      | NO   |     | NULL    |       |
| ET           | datetime      | NO   |     | NULL    |       |
| RT           | datetime      | NO   |     | NULL    |       |
+--------------+---------------+------+-----+---------+-------+
CREATE TABLE pet_bbs.bbs_special_subject (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `gid` varchar(64) NOT NULL COMMENT '组主键',
  `seq` int COMMENT '顺序',
  `name` varchar(2000) DEFAULT NULL COMMENT '标题',
  `summary` varchar(3000) DEFAULT NULL COMMENT '摘要',
  `img` varchar(1000) DEFAULT NULL COMMENT '图片',
  `type` varchar(1000) DEFAULT NULL COMMENT '类型：单个、多个',
  `note_id` varchar(64) DEFAULT NULL COMMENT '帖子ID',
  `state` varchar(64) DEFAULT NULL COMMENT '状态：草稿、完成、已推送、作废',
  `ct` datetime,
  `et` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',
  `eb` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





宠物品种，宠物昵称，宠物主人，主人电话，主人寄语
CREATE TABLE pet_user.pet_card (
  `id` varchar(64) NOT NULL COMMENT '主键-编号',
  `user_id` varchar(200) DEFAULT NULL COMMENT '宠物名片所有者',
  `pet_type` varchar(3000) DEFAULT NULL COMMENT '宠物类型',
  `pet_nickname` varchar(1000) DEFAULT NULL COMMENT '宠物昵称',
  `pet_owner` varchar(1000) DEFAULT NULL COMMENT '宠物主人',
  `pet_owner_tel` varchar(64) DEFAULT NULL COMMENT '主人电话',
  `pet_owner_msg` varchar(2000) DEFAULT NULL COMMENT '寄语',
  `pet_img` varchar(3000) DEFAULT NULL COMMENT '宠物图片',
  `ct` datetime,
  `et` datetime,
  `cb` varchar(200) DEFAULT NULL COMMENT 'create by',
  `eb` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
