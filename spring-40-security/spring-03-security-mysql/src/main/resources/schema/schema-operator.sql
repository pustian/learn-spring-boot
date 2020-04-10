CREATE DATABASE IF NOT EXISTS `spring`;
--SHOW CREATE DATABASE `data_dictionary`;

--DROP TABLE IF EXISTS  `spring`.`operator`;

CREATE TABLE `spring`.`operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  unique index uq_username(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';