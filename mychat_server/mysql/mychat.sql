/*
 Navicat Premium Data Transfer

 Source Server Version : 50734

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 09/06/2021 20:24:21
*/

CREATE DATABASE mychat;
USE mychat;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dw_group
-- ----------------------------
DROP TABLE IF EXISTS `dw_group`;
CREATE TABLE `dw_group`  (
  `group_id` int(10) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `group_master` int(10) NULL DEFAULT NULL,
  `group_signature` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `group_registertime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `group_profile` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `group_master`(`group_master`) USING BTREE,
  CONSTRAINT `fk_masterid_group_user` FOREIGN KEY (`group_master`) REFERENCES `dw_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10005 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_group
-- ----------------------------
INSERT INTO `dw_group` VALUES (10000, 'MyChat小家庭', 10000, 'MyChat', '2021-06-06 14:23:47', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606141831.jpg');
INSERT INTO `dw_group` VALUES (10001, '集合啦！干饭之森', 10003, '干饭人 干饭魂', '2021-06-06 14:27:12', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606142243.jpg');
INSERT INTO `dw_group` VALUES (10003, '大学生摸鱼交流群', 10009, '摸起我心爱的小鱼干', '2021-06-06 14:28:43', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606142240.jpg');
INSERT INTO `dw_group` VALUES (10004, 'MyChat开发测试', 10000, '[内测内容 禁止外泄]', '2021-06-06 14:29:44', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606142258.jpg');

-- ----------------------------
-- Table structure for dw_groupchat
-- ----------------------------
DROP TABLE IF EXISTS `dw_groupchat`;
CREATE TABLE `dw_groupchat`  (
  `gchat_id` int(10) NOT NULL AUTO_INCREMENT,
  `gchat_uid` int(10) NOT NULL,
  `gchat_gid` int(10) NOT NULL,
  `gchat_message` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `gchat_datetime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`gchat_id`) USING BTREE,
  INDEX `gchat_uid`(`gchat_uid`) USING BTREE,
  INDEX `gchat_gid`(`gchat_gid`) USING BTREE,
  CONSTRAINT `fk_groupid_groupchat_group` FOREIGN KEY (`gchat_gid`) REFERENCES `dw_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userid_groupchat_user` FOREIGN KEY (`gchat_uid`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_groupchat
-- ----------------------------
INSERT INTO `dw_groupchat` VALUES (1, 10000, 10000, 'mychat开服了！', '2021-06-06 15:37:18');
INSERT INTO `dw_groupchat` VALUES (2, 10013, 10000, '真不错', '2021-06-06 15:45:38');
INSERT INTO `dw_groupchat` VALUES (3, 10011, 10000, '好', '2021-06-06 15:46:45');

-- ----------------------------
-- Table structure for dw_user
-- ----------------------------
DROP TABLE IF EXISTS `dw_user`;
CREATE TABLE `dw_user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_password` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'M',
  `user_birthday` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `user_profile` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_signature` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `user_registertime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10014 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_user
-- ----------------------------
INSERT INTO `dw_user` VALUES (10000, '晨风', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-2-6', ' https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171922.jpg', '这是我的个性签名', '2021-06-06 13:37:13');
INSERT INTO `dw_user` VALUES (10001, 'Miuna', 'e10adc3949ba59abbe56e057f20f883e', 'W', '2000-1-1', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171800.jpg', '', '2021-06-06 13:37:48');
INSERT INTO `dw_user` VALUES (10002, '奥特man', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-2', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171757.jpg', '我是光', '2021-06-06 13:40:06');
INSERT INTO `dw_user` VALUES (10003, '海绵饱饱', 'e10adc3949ba59abbe56e057f20f883e', 'W', '2000-1-3', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171826.jpg', '干饭不积极 脑壳有问题', '2021-06-06 13:41:40');
INSERT INTO `dw_user` VALUES (10004, '骑电驴载giegie', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-4', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171840.jpg', '骑上我的小摩托', '2021-06-06 13:42:52');
INSERT INTO `dw_user` VALUES (10005, '英年早秃', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-5', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171830.jpg', '一张纸三根发 一本书满头秃', '2021-06-06 13:48:12');
INSERT INTO `dw_user` VALUES (10006, '阿杰', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-6', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606134714.jpg', '你很勇噢', '2021-06-06 13:48:50');
INSERT INTO `dw_user` VALUES (10007, 'Aurora', 'e10adc3949ba59abbe56e057f20f883e', 'W', '2000-1-7', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606135138.jpg', '', '2021-06-06 13:52:34');
INSERT INTO `dw_user` VALUES (10008, '没脸看', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-8', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171826.jpg', '', '2021-06-06 13:53:43');
INSERT INTO `dw_user` VALUES (10009, '让我滚去背英语', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-9', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171818.jpg', 'Impossible is nothing.', '2021-06-06 13:55:12');
INSERT INTO `dw_user` VALUES (10010, '汪汪碎冰冰', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-10', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171749.jpg', '', '2021-06-06 13:55:58');
INSERT INTO `dw_user` VALUES (10011, '冰冰', 'e10adc3949ba59abbe56e057f20f883e', 'W', '2000-1-11', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171753.jpg', '', '2021-06-06 13:56:44');
INSERT INTO `dw_user` VALUES (10012, '青空', 'e10adc3949ba59abbe56e057f20f883e', 'M', '2000-1-12', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210605171804.jpg', '无尽的伟大，亦是从渺小开始', '2021-06-06 13:58:35');
INSERT INTO `dw_user` VALUES (10013, '四月', 'e10adc3949ba59abbe56e057f20f883e', 'W', '2000-1-13', 'https://gitee.com/WindChenCC/Images/raw/master/Images/20210606140259.jpg', '', '2021-06-06 14:04:09');

-- ----------------------------
-- Table structure for dw_userchat
-- ----------------------------
DROP TABLE IF EXISTS `dw_userchat`;
CREATE TABLE `dw_userchat`  (
  `uchat_id` int(10) NOT NULL AUTO_INCREMENT,
  `uchat_fromid` int(10) NOT NULL,
  `uchat_toid` int(10) NOT NULL,
  `uchat_message` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `uchat_datetime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`uchat_id`) USING BTREE,
  INDEX `uchat_fromid`(`uchat_fromid`) USING BTREE,
  INDEX `uchat_toid`(`uchat_toid`) USING BTREE,
  CONSTRAINT `fk_fromid_userchat_user` FOREIGN KEY (`uchat_fromid`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_toid_uerchat_user` FOREIGN KEY (`uchat_toid`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_userchat
-- ----------------------------
INSERT INTO `dw_userchat` VALUES (1, 10001, 10000, 'Hi', '2021-06-06 15:10:40');
INSERT INTO `dw_userchat` VALUES (2, 10000, 10001, '你好', '2021-06-06 15:10:47');
INSERT INTO `dw_userchat` VALUES (3, 10013, 10000, 'Hello', '2021-06-06 15:41:52');

-- ----------------------------
-- Table structure for dw_usergroup
-- ----------------------------
DROP TABLE IF EXISTS `dw_usergroup`;
CREATE TABLE `dw_usergroup`  (
  `user_id` int(10) NOT NULL,
  `group_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`, `group_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  CONSTRAINT `fk_groupid_usergroup_group` FOREIGN KEY (`group_id`) REFERENCES `dw_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userid_usergroup_user` FOREIGN KEY (`user_id`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_usergroup
-- ----------------------------
INSERT INTO `dw_usergroup` VALUES (10000, 10000);
INSERT INTO `dw_usergroup` VALUES (10000, 10001);
INSERT INTO `dw_usergroup` VALUES (10000, 10003);
INSERT INTO `dw_usergroup` VALUES (10000, 10004);
INSERT INTO `dw_usergroup` VALUES (10001, 10000);
INSERT INTO `dw_usergroup` VALUES (10001, 10003);
INSERT INTO `dw_usergroup` VALUES (10001, 10004);
INSERT INTO `dw_usergroup` VALUES (10002, 10000);
INSERT INTO `dw_usergroup` VALUES (10002, 10001);
INSERT INTO `dw_usergroup` VALUES (10002, 10003);
INSERT INTO `dw_usergroup` VALUES (10002, 10004);
INSERT INTO `dw_usergroup` VALUES (10003, 10000);
INSERT INTO `dw_usergroup` VALUES (10003, 10001);
INSERT INTO `dw_usergroup` VALUES (10003, 10003);
INSERT INTO `dw_usergroup` VALUES (10004, 10000);
INSERT INTO `dw_usergroup` VALUES (10004, 10001);
INSERT INTO `dw_usergroup` VALUES (10005, 10000);
INSERT INTO `dw_usergroup` VALUES (10005, 10003);
INSERT INTO `dw_usergroup` VALUES (10006, 10000);
INSERT INTO `dw_usergroup` VALUES (10006, 10001);
INSERT INTO `dw_usergroup` VALUES (10007, 10000);
INSERT INTO `dw_usergroup` VALUES (10007, 10003);
INSERT INTO `dw_usergroup` VALUES (10008, 10000);
INSERT INTO `dw_usergroup` VALUES (10008, 10003);
INSERT INTO `dw_usergroup` VALUES (10009, 10000);
INSERT INTO `dw_usergroup` VALUES (10009, 10003);
INSERT INTO `dw_usergroup` VALUES (10010, 10000);
INSERT INTO `dw_usergroup` VALUES (10011, 10000);
INSERT INTO `dw_usergroup` VALUES (10011, 10001);
INSERT INTO `dw_usergroup` VALUES (10012, 10000);
INSERT INTO `dw_usergroup` VALUES (10012, 10003);
INSERT INTO `dw_usergroup` VALUES (10013, 10000);
INSERT INTO `dw_usergroup` VALUES (10013, 10001);

-- ----------------------------
-- Table structure for dw_useruser
-- ----------------------------
DROP TABLE IF EXISTS `dw_useruser`;
CREATE TABLE `dw_useruser`  (
  `myself` int(10) NOT NULL,
  `myfriend` int(10) NOT NULL,
  PRIMARY KEY (`myself`, `myfriend`) USING BTREE,
  INDEX `myself`(`myself`) USING BTREE,
  INDEX `myfriend`(`myfriend`) USING BTREE,
  CONSTRAINT `fk_friendid_useruser_user` FOREIGN KEY (`myfriend`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_myid_useruser_user` FOREIGN KEY (`myself`) REFERENCES `dw_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dw_useruser
-- ----------------------------
INSERT INTO `dw_useruser` VALUES (10000, 10001);
INSERT INTO `dw_useruser` VALUES (10000, 10002);
INSERT INTO `dw_useruser` VALUES (10000, 10003);
INSERT INTO `dw_useruser` VALUES (10000, 10004);
INSERT INTO `dw_useruser` VALUES (10000, 10005);
INSERT INTO `dw_useruser` VALUES (10000, 10006);
INSERT INTO `dw_useruser` VALUES (10000, 10007);
INSERT INTO `dw_useruser` VALUES (10000, 10008);
INSERT INTO `dw_useruser` VALUES (10000, 10009);
INSERT INTO `dw_useruser` VALUES (10000, 10010);
INSERT INTO `dw_useruser` VALUES (10000, 10011);
INSERT INTO `dw_useruser` VALUES (10000, 10012);
INSERT INTO `dw_useruser` VALUES (10000, 10013);
INSERT INTO `dw_useruser` VALUES (10001, 10000);
INSERT INTO `dw_useruser` VALUES (10001, 10002);
INSERT INTO `dw_useruser` VALUES (10001, 10004);
INSERT INTO `dw_useruser` VALUES (10001, 10005);
INSERT INTO `dw_useruser` VALUES (10001, 10006);
INSERT INTO `dw_useruser` VALUES (10001, 10010);
INSERT INTO `dw_useruser` VALUES (10001, 10011);
INSERT INTO `dw_useruser` VALUES (10001, 10012);
INSERT INTO `dw_useruser` VALUES (10001, 10013);
INSERT INTO `dw_useruser` VALUES (10002, 10000);
INSERT INTO `dw_useruser` VALUES (10002, 10001);
INSERT INTO `dw_useruser` VALUES (10002, 10003);
INSERT INTO `dw_useruser` VALUES (10002, 10005);
INSERT INTO `dw_useruser` VALUES (10002, 10009);
INSERT INTO `dw_useruser` VALUES (10003, 10000);
INSERT INTO `dw_useruser` VALUES (10003, 10002);
INSERT INTO `dw_useruser` VALUES (10003, 10004);
INSERT INTO `dw_useruser` VALUES (10003, 10006);
INSERT INTO `dw_useruser` VALUES (10003, 10008);
INSERT INTO `dw_useruser` VALUES (10003, 10010);
INSERT INTO `dw_useruser` VALUES (10004, 10000);
INSERT INTO `dw_useruser` VALUES (10004, 10001);
INSERT INTO `dw_useruser` VALUES (10004, 10003);
INSERT INTO `dw_useruser` VALUES (10004, 10005);
INSERT INTO `dw_useruser` VALUES (10004, 10007);
INSERT INTO `dw_useruser` VALUES (10004, 10009);
INSERT INTO `dw_useruser` VALUES (10004, 10011);
INSERT INTO `dw_useruser` VALUES (10005, 10000);
INSERT INTO `dw_useruser` VALUES (10005, 10001);
INSERT INTO `dw_useruser` VALUES (10005, 10002);
INSERT INTO `dw_useruser` VALUES (10005, 10004);
INSERT INTO `dw_useruser` VALUES (10005, 10006);
INSERT INTO `dw_useruser` VALUES (10005, 10008);
INSERT INTO `dw_useruser` VALUES (10005, 10010);
INSERT INTO `dw_useruser` VALUES (10005, 10012);
INSERT INTO `dw_useruser` VALUES (10006, 10000);
INSERT INTO `dw_useruser` VALUES (10006, 10001);
INSERT INTO `dw_useruser` VALUES (10006, 10003);
INSERT INTO `dw_useruser` VALUES (10006, 10007);
INSERT INTO `dw_useruser` VALUES (10006, 10009);
INSERT INTO `dw_useruser` VALUES (10006, 10011);
INSERT INTO `dw_useruser` VALUES (10006, 10012);
INSERT INTO `dw_useruser` VALUES (10006, 10013);
INSERT INTO `dw_useruser` VALUES (10007, 10000);
INSERT INTO `dw_useruser` VALUES (10007, 10004);
INSERT INTO `dw_useruser` VALUES (10007, 10006);
INSERT INTO `dw_useruser` VALUES (10007, 10008);
INSERT INTO `dw_useruser` VALUES (10007, 10010);
INSERT INTO `dw_useruser` VALUES (10007, 10012);
INSERT INTO `dw_useruser` VALUES (10008, 10000);
INSERT INTO `dw_useruser` VALUES (10008, 10003);
INSERT INTO `dw_useruser` VALUES (10008, 10005);
INSERT INTO `dw_useruser` VALUES (10008, 10007);
INSERT INTO `dw_useruser` VALUES (10008, 10009);
INSERT INTO `dw_useruser` VALUES (10008, 10011);
INSERT INTO `dw_useruser` VALUES (10008, 10013);
INSERT INTO `dw_useruser` VALUES (10009, 10000);
INSERT INTO `dw_useruser` VALUES (10009, 10002);
INSERT INTO `dw_useruser` VALUES (10009, 10004);
INSERT INTO `dw_useruser` VALUES (10009, 10006);
INSERT INTO `dw_useruser` VALUES (10009, 10008);
INSERT INTO `dw_useruser` VALUES (10009, 10010);
INSERT INTO `dw_useruser` VALUES (10009, 10012);
INSERT INTO `dw_useruser` VALUES (10010, 10000);
INSERT INTO `dw_useruser` VALUES (10010, 10001);
INSERT INTO `dw_useruser` VALUES (10010, 10003);
INSERT INTO `dw_useruser` VALUES (10010, 10005);
INSERT INTO `dw_useruser` VALUES (10010, 10007);
INSERT INTO `dw_useruser` VALUES (10010, 10011);
INSERT INTO `dw_useruser` VALUES (10010, 10012);
INSERT INTO `dw_useruser` VALUES (10010, 10013);
INSERT INTO `dw_useruser` VALUES (10011, 10000);
INSERT INTO `dw_useruser` VALUES (10011, 10001);
INSERT INTO `dw_useruser` VALUES (10011, 10004);
INSERT INTO `dw_useruser` VALUES (10011, 10006);
INSERT INTO `dw_useruser` VALUES (10011, 10008);
INSERT INTO `dw_useruser` VALUES (10011, 10010);
INSERT INTO `dw_useruser` VALUES (10011, 10012);
INSERT INTO `dw_useruser` VALUES (10012, 10000);
INSERT INTO `dw_useruser` VALUES (10012, 10001);
INSERT INTO `dw_useruser` VALUES (10012, 10005);
INSERT INTO `dw_useruser` VALUES (10012, 10006);
INSERT INTO `dw_useruser` VALUES (10012, 10007);
INSERT INTO `dw_useruser` VALUES (10012, 10009);
INSERT INTO `dw_useruser` VALUES (10012, 10010);
INSERT INTO `dw_useruser` VALUES (10012, 10011);
INSERT INTO `dw_useruser` VALUES (10012, 10013);
INSERT INTO `dw_useruser` VALUES (10013, 10000);
INSERT INTO `dw_useruser` VALUES (10013, 10001);
INSERT INTO `dw_useruser` VALUES (10013, 10006);
INSERT INTO `dw_useruser` VALUES (10013, 10008);
INSERT INTO `dw_useruser` VALUES (10013, 10010);
INSERT INTO `dw_useruser` VALUES (10013, 10012);

-- ----------------------------
-- View structure for view_usergroup
-- ----------------------------
DROP VIEW IF EXISTS `view_usergroup`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_usergroup` AS (select `dw_group`.`group_id` AS `group_id`,`dw_group`.`group_name` AS `group_name`,`dw_group`.`group_profile` AS `group_profile`,`dw_group`.`group_signature` AS `group_signature`,`dw_usergroup`.`user_id` AS `user_id`,`dw_user`.`user_name` AS `user_name`,`dw_user`.`user_profile` AS `user_profile` from ((`dw_group` join `dw_usergroup`) join `dw_user`) where ((`dw_group`.`group_id` = `dw_usergroup`.`group_id`) and (`dw_usergroup`.`user_id` = `dw_user`.`user_id`)));

-- ----------------------------
-- View structure for view_useruser
-- ----------------------------
DROP VIEW IF EXISTS `view_useruser`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_useruser` AS (select `dw_useruser`.`myself` AS `myself`,`dw_useruser`.`myfriend` AS `myfriend`,`dw_user`.`user_name` AS `user_name`,`dw_user`.`user_profile` AS `user_profile`,`dw_user`.`user_signature` AS `user_signature` from (`dw_user` join `dw_useruser`) where (`dw_user`.`user_id` = `dw_useruser`.`myfriend`) order by `dw_useruser`.`myself`);

SET FOREIGN_KEY_CHECKS = 1;
