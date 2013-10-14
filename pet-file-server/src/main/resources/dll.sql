create database pet_fileserver;
--文件索引
CREATE TABLE `file_index` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `file_name` varchar(500) DEFAULT NULL COMMENT '名称',
  `file_type` varchar(30) DEFAULT NULL COMMENT '扩展名',
  `file_path` varchar(2000) DEFAULT NULL COMMENT '路径',
  `file_src` varchar(2000) DEFAULT NULL COMMENT '来源',
  `ct` timestamp ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;