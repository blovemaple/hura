CREATE DATABASE IF NOT EXISTS `hura`;

CREATE TABLE `hura`.`abonkonto_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `status` varchar(7) NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  `msg_type` varchar(15) NOT NULL,
  `request` text NOT NULL,
  `response` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `hura`.`programeto_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `code` varchar(63) NOT NULL,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `hura`.`programeto_query_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  `query` varchar(1024) NOT NULL,
  `section_key` varchar(31) NOT NULL,
  `has_result` tinyint(1) NOT NULL,
  `result` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;