CREATE DATABASE IF NOT EXISTS `data_dictionary`;
SHOW CREATE DATABASE `data_dictionary`;

INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('记录id', 'record id', 'id', 1, 'id', 1, 'id', 1, 'id', '记录的id,自增', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('创建时间', 'record create time', 'gmt_create', 6, 'gmt_create', 6, 'gmtCreate', 6, 'gmtCreate', '记录的创建时间', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('更新时间', 'record modified time', 'gmt_modified', 6, 'gmt_modified', 6, 'gmtModified', 6, 'gmtModified', '记录的更新时间', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');

INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('sql 数据类型', 'sql type', 'sql_type', 0, 'sql_type', 0, 'sql_type', 0, 'sqlType', 'sql数据类型: 整形, 字符串, 数值, bool, DATE, TIME', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('c 数据类型', 'c type', 'c_type', 0, 'c_type', 0, 'cType', 0, 'cType', 'c数据类型: 整形, 字符串, 数值, bool, DATE, TIME', 0, 0, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('java 数据类型', 'java type', 'java_type', 0, 'java_type', 0, 'javaType', 0, 'javaType', 'Java 数据类型: 整形, 字符串, 数值, bool, DATE, TIME', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('数据项类型', 'element type', 'element_type', 0, 'element_type', 0, 'elementType', 0, 'elementType', '数据类型: 0 技术字段, 1 业务字段', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('数据项状态', 'element status', 'element_status', 0, 'element_status', 0, 'elementStatus', 0, 'elementStatus', '数据状态: 0 申请中, 1 使用, 9 废弃', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.`element`
(name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, `type`, status, gmt_create, gmt_modified)
VALUES('记录是否删除', 'record delete', 'is_deleted', 0, 'is_deleted', 0, 'isDeleted', 0, 'isDeleted', '记录状态: 0 未删除, 1 已删除', 0, 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');

-- `data_dictionary`.element_item
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(1, 0, '整数', 'int', 'intger', NULL, 'int', NULL, 'Integer', NULL, '', 'sql类型为整数', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(2, 0, '字符串', 'string', 'varchar', NULL, 'char[]', NULL, 'String', NULL, '', 'sql类型为字符串', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(3, 0, '定长字符串', 'fixed length string', 'char', NULL, 'char[]', NULL, 'String', NULL, '', 'sql类型为定长字符串', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(4, 0, '精确数值', 'decimal', 'decimal', NULL, 'struct', NULL, 'BigDecimal', NULL, '', 'sql类型为精确数值', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(5, 0, '布尔值', 'boolean', 'boolean', NULL, 'int', NULL, 'String', NULL, '', 'sql类型为boolean', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(6, 0, '日期时间', 'datetime', 'datetime', NULL, 'struct tm', NULL, 'Date', NULL, '', 'sql类型为日期时间', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');
INSERT INTO `data_dictionary`.element_item
(id, element_id, name, name_en, name_sql, sql_type, name_c, c_type, name_java, java_type, name_js, description, status, gmt_create, gmt_modified)
VALUES(7, 0, '时间戳', 'timestamp', 'timestamp', NULL, 'time_t', NULL, 'Long', NULL, '', 'sql类型为时间戳', 1, '1970-01-01 01:01:01', '1970-01-01 01:01:01');


