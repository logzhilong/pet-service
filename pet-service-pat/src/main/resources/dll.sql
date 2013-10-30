DROP TABLE IF EXISTS `user_pat`;
CREATE TABLE `user_pat` (
  `id` varchar(60) NOT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `src_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `ct` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
