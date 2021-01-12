-- -----------------------------
-- 菜单表
-- -----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE `sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `href` varchar(100) NOT NULL DEFAULT '' COMMENT '链接',
  `target` varchar(20) NOT NULL DEFAULT '_self' COMMENT '链接打开方式',
  `sort` int(11) DEFAULT '0' COMMENT '菜单排序',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态(0:禁用,1:启用)',
  `isMenu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态(0:菜单,1:按钮)',
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `href` (`href`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单表';
-- -----------------------------
-- 菜单表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`) VALUES (200, 0, '人事管理', '', '', '_self', 0, 1, 0);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`) VALUES (201, 0, '考勤管理', '', '', '_self', 0, 1, 0);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`) VALUES (202, 0, '其他管理', '', '', '_self', 0, 1, 0);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`) VALUES (203, 202, '菜单管理', 'fa fa-th-large', '/menu/manager', '_self', 1, 1, 1);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`) VALUES (204, 202, '用户管理', 'fa fa-user', '', '_self', 2, 1, 0);


-- -----------------------------
-- 用户表
-- -----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` bigint(11) unsigned  DEFAULT '0' COMMENT '手机号',
  `email` varchar(100)  DEFAULT '' COMMENT '邮箱',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- -----------------------------
-- 用户表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`) VALUES (1, 17628172559, '1317524710@qq.com', '123456q', '饭团');
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`) VALUES (2, NULL, '212025682@qq.com', '123456q', '橙子');
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`) VALUES (3, NULL, '1664904182@qq.com', '123456q', '姚灿');
