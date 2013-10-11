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
  `ct` timestamp,
  `et` timestamp,
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
insert mgr_user (id,name,password,cb,eb,ct,et) values('root','root','5NKa8wXQN8BeBdUQOjQqquZPjvAy0A5BJyvQIQkd6/I=','root','root','2013-10-10','2013-10-10');
insert into mgr_role (id,code,name,desct,ct,et) value ('root','root','管理员','超级用户','2013-10-10','2013-10-10')
insert into mgr_user_role_rel (id,user_id,role_id)values('root','root','root');
