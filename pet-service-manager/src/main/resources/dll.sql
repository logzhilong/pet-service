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

