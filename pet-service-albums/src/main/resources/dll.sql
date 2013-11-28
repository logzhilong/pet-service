create database pet_albums;

CREATE TABLE `albums` (
  `id` varchar(64) NOT NULL,
  `userid` varchar(200) DEFAULT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `descript` varchar(2000) DEFAULT NULL,
  `state` varchar(200) DEFAULT NULL,
  `ct` datetime,
  `et` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `photos` (
  `id` varchar(64) NOT NULL,
  `userid` varchar(200) DEFAULT NULL,
  `album_id` varchar(200) DEFAULT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `descript` varchar(2000) DEFAULT NULL,
  `state` varchar(200) DEFAULT NULL,
  `total_click` int ,
  `width` int ,
  `height` int ,
  `ct` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
