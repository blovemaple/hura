CREATE DATABASE IF NOT EXISTS `hura`;

CREATE TABLE `hura`.`user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  `nick_name` varchar(63) NOT NULL DEFAULT '',
  `avatar_url` varchar(1024) NOT NULL DEFAULT '',
  `gender` tinyint(3) NOT NULL DEFAULT 0,
  `country` varchar(63) NOT NULL DEFAULT '',
  `province` varchar(63) NOT NULL DEFAULT '',
  `city` varchar(63) NOT NULL DEFAULT '',
  `language` varchar(63) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_openid_IDX` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hura`.`programeto_login_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `code` varchar(63) NOT NULL,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hura`.`programeto_query_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  `openid` varchar(63) NOT NULL,
  `unionid` varchar(63) NOT NULL DEFAULT '',
  `query` varchar(1024) NOT NULL,
  `section_key` varchar(31) NOT NULL,
  `is_detail` tinyint(1) NOT NULL DEFAULT 0,
  `has_result` tinyint(1) NOT NULL,
  `result` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hura`.`vortlisto` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(63) NOT NULL,
  `name` varchar(63) NOT NULL,
  `is_del` tinyint(1) NOT NULL,
  `addtime` datetime NOT NULL,
  `modtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `vortlisto_openid_IDX` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hura`.`vortlisto_vorto` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vortlisto_id` bigint(20) unsigned NOT NULL,
  `openid` varchar(63) NOT NULL,
  `vorto` varchar(63) NOT NULL,
  `signifo` text NOT NULL,
  `is_del` tinyint(1) NOT NULL,
  `addtime` datetime NOT NULL,
  `modtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vortlisto_vorto_vortlisto_id_IDX` (`vortlisto_id`,`vorto`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hura`.`user_config` (
  `openid` varchar(63) NOT NULL,
  `def_vortlisto_id` bigint(20) DEFAULT NULL,
  `show_query_history` tinyint(1) NOT NULL,
  `hide_section_keys` varchar(255) NOT NULL,
  `modtime` datetime NOT NULL,
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;