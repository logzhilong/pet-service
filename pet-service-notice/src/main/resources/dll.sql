create database pet_notice;

CREATE TABLE `notice` (
  `id` varchar(64) NOT NULL,
  `name` varchar(500) DEFAULT NULL,
  `pid` varchar(64) DEFAULT NULL,
  `pname` varchar(500) DEFAULT NULL,
  `info` text DEFAULT NULL,
  `seq` int,
  `ct` datetime,
  `cb` varchar(500) DEFAULT NULL,
  `et` datetime,
  `eb` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
