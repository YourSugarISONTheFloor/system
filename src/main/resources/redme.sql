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
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态(0:禁用,1:启用)',
  `isMenu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态(0:菜单,1:按钮)',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `href` (`href`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单表';

-- -----------------------------
-- 菜单表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (200, 0, '基本管理', '', '', '_self', 0, 1, 0, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (201, 0, '考勤管理', '', '', '_self', 0, 1, 0, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (202, 0, '其他管理', '', '', '_self', 0, 1, 0, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (203, 202, '菜单管理', 'fa fa-th-large', 'menu/manager', '_self', 1, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (204, 202, '用户管理', 'fa fa-database', '', '_self', 2, 1, 0, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (205, 204, '权限管理', '', 'user/role', '_self', 1, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (206, 204, '部门管理', '', 'user/dept', '_self', 2, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (207, 204, '角色管理', '', 'user/part', '_self', 3, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (208, 200, '伤病请假', 'fa fa-window-maximize', 'seat/Injury', '_self', 1, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (209, 200, '签到打卡', 'fa fa-camera-retro', 'seat/sign', '_self', 2, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (210, 200, '代办事项', 'fa fa-calendar-o', 'seat/matter', '_self', 3, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (211, 202, '用户关系', 'fa fa-calendar-o', 'consumer/tree', '_self', 3, 1, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (218, 201, '考勤统计', 'fa fa-camera', 'work/statistics', '_self', 1, 0, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (219, 201, '考勤制定', 'fa fa-clock-o', 'work/arrange', '_self', 2, 0, 1, NULL);
INSERT INTO `system`.`sys_menu`(`id`, `pid`, `title`, `icon`, `href`, `target`, `sort`, `status`, `isMenu`, `permission`) VALUES (220, 201, '考勤规则', 'fa fa-puzzle-piece', 'work/rule', '_self', 3, 0, 1, NULL);


-- -----------------------------
-- 用户表
-- -----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` bigint(11) unsigned DEFAULT '0' COMMENT '手机号',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名称',
  `deptId` int(11) DEFAULT NULL COMMENT '部门ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态（0：正常(默认值)，1：禁用，2：锁定',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- -----------------------------
-- 用户表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`, `deptId`, `status`, `avatar`) VALUES (1, 17600000000, '123@qq.com', '123456q', '圆子', 3, 0, NULL);
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`, `deptId`, `status`, `avatar`) VALUES (2, 17628172559, '1317524710@qq.com', '123456q', '饭团', 4, 0, NULL);
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`, `deptId`, `status`, `avatar`) VALUES (3, NULL, '212025682@qq.com', '123456q', '橙子', 5, 1, NULL);
INSERT INTO `system`.`sys_user`(`id`, `phone`, `email`, `password`, `name`, `deptId`, `status`, `avatar`) VALUES (4, NULL, '1664904182@qq.com', '123456q', '姚灿', 5, 1, NULL);


-- -----------------------------
-- 角色表
-- -----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- -----------------------------
-- 角色表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_role`(`role_id`, `name`, `remark`) VALUES (1, '超级管理员', '系统最高权限');
INSERT INTO `system`.`sys_role`(`role_id`, `name`, `remark`) VALUES (2, '总经理', 'CEO');
INSERT INTO `system`.`sys_role`(`role_id`, `name`, `remark`) VALUES (3, '项目经理', '研发部一把手');
INSERT INTO `system`.`sys_role`(`role_id`, `name`, `remark`) VALUES (4, '研发者', '最低级的码农');
INSERT INTO `system`.`sys_role`(`role_id`, `name`, `remark`) VALUES (5, '人事', '招聘头头');


-- -----------------------------
-- 部门表
-- -----------------------------
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级部门ID',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


-- -----------------------------
-- 部门表初始数据
-- -----------------------------
INSERT INTO `system`.`sys_dept`(`dept_id`, `pid`, `name`) VALUES (1, 0, '长老会');
INSERT INTO `system`.`sys_dept`(`dept_id`, `pid`, `name`) VALUES (2, 1, '经理部');
INSERT INTO `system`.`sys_dept`(`dept_id`, `pid`, `name`) VALUES (3, 2, '项目部');
INSERT INTO `system`.`sys_dept`(`dept_id`, `pid`, `name`) VALUES (4, 3, '研发部');
INSERT INTO `system`.`sys_dept`(`dept_id`, `pid`, `name`) VALUES (5, 4, '人事部');
