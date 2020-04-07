CREATE DATABASE IF NOT EXISTS data_dictionary;
SHOW CREATE DATABASE data_dictionary;

drop table if exists `data_dictionary`.`test` ;
drop table if exists `data_dictionary`.`element` ;
drop table if exists `data_dictionary`.`element_item` ;
--drop table if exists `data_dictionary`.`element_log` ;
--drop table if exists `data_dictionary`.`department` ;
--drop table if exists `data_dictionary`.`employee` ;

create table `data_dictionary`.`test`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `gmt_create` datetime DEFAULT NULL,
    `gmt_modified` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表';

create table `data_dictionary`.`element`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) DEFAULT NULL,
    `name_en` varchar(32) DEFAULT NULL,
    `name_sql` varchar(32) DEFAULT NULL,
    `sql_type` tinyint DEFAULT NULL COMMENT '0 等价于 null',
    `name_c` varchar(32) DEFAULT NULL,
    `c_type` tinyint DEFAULT NULL COMMENT '0 等价于 null',
    `name_java` varchar(32) DEFAULT NULL,
    `java_type` tinyint DEFAULT NULL COMMENT '0 等价于 null',
    `name_js` varchar(32) DEFAULT NULL,
    `description` varchar(128) DEFAULT NULL,
    `type` tinyint NOT NULL COMMENT '0-技术字段,1-业务字段',
    `status` tinyint DEFAULT 0 COMMENT '0-不可用,1-使用, 9-推荐不再使用',
    `gmt_create` datetime DEFAULT NULL,
    `gmt_modified` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE index name_idx (name),
    index description_idx (description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据项';

create table `data_dictionary`.`element_item`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `element_id` int(11) NOT NULL COMMENT '0 基础字段',
    `name` varchar(32) DEFAULT NULL,
    `name_en` varchar(32) DEFAULT NULL,
    `name_sql` varchar(32) DEFAULT NULL,
    `sql_type` tinyint DEFAULT NULL,
    `name_c` varchar(32) DEFAULT NULL,
    `c_type` tinyint DEFAULT NULL,
    `name_java` varchar(32) DEFAULT NULL,
    `java_type` tinyint DEFAULT NULL,
    `name_js` varchar(32) DEFAULT NULL,
    `description` varchar(128) DEFAULT NULL,
    `status` tinyint DEFAULT 0 COMMENT '0-不可用,1-使用, 9-推荐不再使用',
    `gmt_create` datetime DEFAULT NULL,
    `gmt_modified` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    index element_idx (element_id),
    UNIQUE index name_idx (name, element_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据项明细';
--
--create table `data_dictionary`.`department`  (
--    `id` int(11) NOT NULL AUTO_INCREMENT,
--    `name` varchar(32) DEFAULT NULL,
--    `leader` varchar(32) DEFAULT NULL,
--    `remark` varchar(32) DEFAULT NULL,
--    `deleted` tinyint DEFAULT 0 COMMENT '0-未删除,1-已删除',
--    `gmt_create` datetime DEFAULT NULL,
--    `gmt_modified` datetime DEFAULT NULL,
--    PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';
--
--create table `data_dictionary`.`employee`  (
--    `id` int(11) NOT NULL AUTO_INCREMENT,
--    `department_id` int(11) NOT NULL,
--    `name` varchar(32) DEFAULT NULL,
--    `email` varchar(32) DEFAULT NULL,
--    `phone` varchar(32) DEFAULT NULL,
--    `address` varchar(32) DEFAULT NULL,
--    `leader_id` int(11) NOT NULL,
--    `rank` tinyint NOT NULL COMMENT '6级以上可以直接生效',
--    `passwd` varchar(64) NOT NULL,
--    `remark` varchar(32) NOT NULL,
--    `deleted` tinyint DEFAULT 0 COMMENT '0-未删除,1-已删除',
--    `gmt_create` datetime DEFAULT NULL,
--    `gmt_modified` datetime DEFAULT NULL,
--    PRIMARY KEY (`id`),
--    index department_id_idx (department_id),
--    index name_idx (name)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';
--
--create table `data_dictionary`.`element_log`  (
--    `id` int(11) NOT NULL AUTO_INCREMENT,
--    `element_id` int(11) NOT NULL,
--    `employee_id` int(11) NOT NULL,
--    `remark` varchar(64) NOT NULL,
--    `gmt_create` datetime DEFAULT NULL,
--    `gmt_modified` datetime DEFAULT NULL,
--    PRIMARY KEY (`id`),
--    index element_id_idx (element_id)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典操作日志表';
