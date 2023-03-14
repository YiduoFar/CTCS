/*
 Navicat Premium Data Transfer

 Source Server         : Windows 10
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : graduation_project_db

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 14/03/2023 23:38:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `activity_id` int NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `activity_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动简介',
  `activity_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动封面',
  `activity_type` tinyint NULL DEFAULT 0 COMMENT '类型 0团建 1聚会 2运动 3社团 4户外 5游戏',
  `activity_joined_is_open` tinyint NULL DEFAULT 0 COMMENT '活动报名是否开启，0关闭，1开启',
  `activity_status` tinyint NULL DEFAULT 1 COMMENT '状态，0已结束，1进行中',
  `activity_stage` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '步骤（JSON化的步骤对象的数组）',
  `activity_information_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容（JSON化的内容对象的数组）',
  `is_team_up` tinyint NULL DEFAULT 0 COMMENT '是否需要组队参加 0个人 1组队',
  `sign_up_min_team_member_num` int NULL DEFAULT NULL COMMENT '队伍最少人数',
  `sign_up_max_team_member_num` int NULL DEFAULT NULL COMMENT '队伍最多人数',
  `activity_audience` tinyint NULL DEFAULT 0 COMMENT '受众，0面向全校，1面向学院、2面向专业、3面向个人',
  `activity_audience_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体受众',
  `activity_joined_num` int NULL DEFAULT 0 COMMENT '已报名数量',
  `activity_stage_now` tinyint NULL DEFAULT 0 COMMENT '当前步骤',
  `activity_initiator_id` int NULL DEFAULT NULL COMMENT '发起人id',
  `is_online` tinyint NULL DEFAULT 0 COMMENT '是否线上 0线下 1线上',
  `activity_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地点',
  `activity_begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `activity_end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `activity_points` int NULL DEFAULT NULL COMMENT '活动加分',
  `activity_publish_date` timestamp NULL DEFAULT '2023-01-01 00:00:00' COMMENT '活动发布时间',
  PRIMARY KEY (`activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (30, '星光遇见你2023校园行', '一起来参加吧！', 'http://localhost:8088/resources/activity_30.jpg', 1, 1, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 1, '信息与通信工程学院', 2, 4, 19, 0, '海燕广场', '2023-02-01 00:00:00', '2023-02-04 00:00:00', 0, '2023-03-09 18:10:33');
INSERT INTO `activity` VALUES (31, '庆新年迎新春大型演讲', '为了迎接新春的到来，特举办此次活动，望各位同学积极参与~', 'http://localhost:8088/resources/activity_31.jpg', 3, 1, 1, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"报名\",\"activityStageBeginTime\":\"2023-03-22 00:00:00\",\"activityStageEndTime\":\"2023-03-22 00:00:00\"},{\"activityStageName\":\"审核资料\",\"activityStageIntro\":\"审核资料\",\"activityStageBeginTime\":\"2023-03-23 00:00:00\",\"activityStageEndTime\":\"2023-03-23 00:00:00\"},{\"activityStageName\":\"公布初选\",\"activityStageIntro\":\"公布初选\",\"activityStageBeginTime\":\"2023-03-24 00:00:00\",\"activityStageEndTime\":\"2023-03-24 00:00:00\"},{\"activityStageName\":\"比赛签到\",\"activityStageIntro\":\"比赛签到\",\"activityStageBeginTime\":\"2023-03-25 00:00:00\",\"activityStageEndTime\":\"2023-03-25 00:00:00\"},{\"activityStageName\":\"公布获奖\",\"activityStageIntro\":\"公布获奖\",\"activityStageBeginTime\":\"2023-03-26 00:00:00\",\"activityStageEndTime\":\"2023-03-26 00:00:00\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"},{\"infoTitle\":\"注意事项\",\"infoContent\":\"演讲注意事项：\\n1.脱稿\\n2.需自备演出服\\n3.在2023年3月23日前把演讲名称与背景发至QQ邮箱：875461946@qq.com\"}]', 0, 0, 0, 1, '轮机学院', 1, 0, 16, 0, '会议室', '2023-03-23 00:00:00', '2023-03-31 00:00:00', 2, '2023-03-09 21:36:15');
INSERT INTO `activity` VALUES (32, '校园第3届LPL电子竞技活动', 'LPL无畏竞巅峰', '', 5, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 1, 5, 5, 0, '', 0, 4, 16, 1, '线上', '2023-03-16 00:00:00', '2023-03-16 00:00:00', 0, '2023-03-14 13:40:03');
INSERT INTO `activity` VALUES (33, '信通学院2023.2图书阅读会', '带上你的爱书，一起来参加图书阅读会吧！', '', 3, 1, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, '', 0, 4, 16, 1, '线上', '2023-03-24 00:00:00', '2023-03-24 00:00:00', 0, '2023-03-14 13:43:35');
INSERT INTO `activity` VALUES (34, '2023校园招聘会', '2023春招开始啦，带上简历来参加吧！', '', 4, 1, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, '', 0, 4, 16, 0, '海燕广场', '2023-03-24 00:00:00', '2023-03-24 00:00:00', 0, '2023-03-14 13:45:30');
INSERT INTO `activity` VALUES (35, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, ' ', 0, 4, 31, 1, '线上', '2023-02-01 00:00:00', '2023-02-01 00:00:00', 0, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (36, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, ' ', 0, 4, 32, 1, '线上', '2023-02-01 00:00:00', '2023-02-01 00:00:00', 0, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (37, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, ' ', 0, 4, 33, 1, '线上', '2023-02-01 00:00:00', '2023-02-01 00:00:00', 0, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (38, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, ' ', 0, 4, 37, 1, '线上', '2023-02-01 00:00:00', '2023-02-01 00:00:00', 0, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (39, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, ' ', 0, 4, 34, 1, '线上\r\n线上', '2023-02-01 00:00:00', '2023-02-01 00:00:00', 0, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (40, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, NULL, NULL, 0, NULL, 0, 4, 17, 0, NULL, NULL, NULL, NULL, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (41, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, NULL, NULL, 0, NULL, 0, 4, 18, 0, NULL, NULL, NULL, NULL, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (42, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, NULL, NULL, 0, NULL, 0, 4, 19, 0, NULL, NULL, NULL, NULL, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (43, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, NULL, NULL, 0, NULL, 0, 4, 20, 0, NULL, NULL, NULL, NULL, '2023-01-01 00:00:00');
INSERT INTO `activity` VALUES (44, '活动测试1', '活动简介', NULL, 0, 0, 0, '[{\"activityStageName\":\"报名\",\"activityStageIntro\":\"平台报名\",\"activityStageBeginTime\":\"2023-02-01 00:00:00\",\"activityStageEndTime\":\"2023-02-03 00:00:00\"},{\"activityStageName\":\"集合\",\"activityStageIntro\":\"线下集合\",\"activityStageBeginTime\":\"2023-02-04 00:15:00\",\"activityStageEndTime\":\"2023-02-04 00:16:00\"},{\"activityStageName\":\"签到\",\"activityStageIntro\":\"纸质签到\",\"activityStageBeginTime\":\"2023-02-04 00:16:00\",\"activityStageEndTime\":\"2023-02-04 00:17:00\"},{\"activityStageName\":\"活动进行\",\"activityStageIntro\":\"\",\"activityStageBeginTime\":\"2023-02-04 00:18:00\",\"activityStageEndTime\":\"2023-02-04 00:21:00\"},{\"activityStageName\":\"结束\",\"activityStageIntro\":\"纸质签退\",\"activityStageBeginTime\":\"2023-02-04 00:22:00\",\"activityStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：活动目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, NULL, NULL, 0, NULL, 0, 4, 23, 0, NULL, NULL, NULL, NULL, '2023-01-01 00:00:00');

-- ----------------------------
-- Table structure for activity_file
-- ----------------------------
DROP TABLE IF EXISTS `activity_file`;
CREATE TABLE `activity_file`  (
  `activity_file_id` int NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `activity_id` int NOT NULL COMMENT '活动id',
  `activity_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `activity_file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `activity_file_announce_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`activity_file_id`, `activity_id`) USING BTREE,
  INDEX `fk_activity_id_3`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `fk_activity_id_3` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_file
-- ----------------------------
INSERT INTO `activity_file` VALUES (5, 31, '活动获奖名单.xlsx', 'http://localhost:8088/resources/31_活动获奖名单.xlsx', '2023-03-09 21:42:55');
INSERT INTO `activity_file` VALUES (6, 31, '活动入选名单.xlsx', 'http://localhost:8088/resources/31_活动入选名单.xlsx', '2023-03-09 21:42:55');

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `announcement_id` int NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `announcement_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目',
  `announcement_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `announcement_publish_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`announcement_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (41, ' 广州航海学院 广州交通大学（筹） 诚邀您依托我校申报海外优青项目', '<p>为进一步完善科学基金人才资助体系，充分发挥科学基金引进和培养人才的功能，吸引海外优秀青年人才回国（来华）工作，国家自然科学基金委员会2023年继续实施国家自然科学基金优秀青年科学基金项目（海外）。旨在吸引和鼓励在自然科学、工程技术等方面已取得较好成绩的海外优秀青年学者（含非华裔外籍人才）回国（来华）工作，自主选择研究方向开展创新性研究，促进青年科学技术人才的快速成长，培养一批有望进入世界科技前沿的优秀学术骨干，为科技强国建设贡献力量。结合学校具体情况，诚邀全球英才依托我校申报2023年优秀青年科学基金项目（海外）。</p>\n<h2><a id=\"__2\"></a>一、 学校介绍</h2>\n<p>广州航海学院创办于1964年，是广州市人民政府管理的公办普通本科院校，是华南地区唯一一所独立建制的海事本科院校，广东省硕士学位授予立项建设单位，国际海事组织认可的航海院校，我国华南地区航运业高级人才重要的培养基地，被誉为“航海家的摇篮”。2018年，广东省委、广州市委明确以广州航海学院为基础筹建广州交通大学。2020年12月教育部、广东省人民政府联合发布《推进粤港澳大湾区高等教育合作发展规划》，明确“推动建设广州交通大学等高校”、“支持广州建设广州交通大学”，将广州建设广州交通大学列入重点项目清单。2021年5月《广州市国民经济和社会发展第十四个五年规划和2035年远景目标纲要》提出：加快建设广州交通大学，打造高水平有特色的应用型大学。</p>\n<h2><a id=\"_6\"></a>二、项目介绍</h2>\n<p>优秀青年科学基金项目（海外）旨在吸引和鼓励在自然科学、工程技术等方面已取得较好成绩的海外优秀青年学者（含非华裔外籍人才）回国（来华）工作，自主选择研究方向开展创新性研究，促进青年科学技术人才的快速成长，培养一批有望进入世界科技前沿的优秀学术骨干，为科技强国建设贡献力量。</p>\n<h2><a id=\"_9\"></a>三、申请条件</h2>\n<h3><a id=\"_10\"></a>（一）申请人应当具备以下条件</h3>\n<h4><a id=\"1_12\"></a>1.遵守中华人民共和国法律法规，具有良好的科学道德，自觉践行新时代科学家精神；</h4>\n<p>2.出生日期在1983年1月1日（含）以后；</p>\n<p>3.具有博士学位；</p>\n<p>4.研究方向主要为自然科学、工程技术等；</p>\n<p>5.在取得博士学位后至2023年3月15日前，一般应在海外高校、科研机构、企业研发机构获得正式教学或者科研职位，原则上具有连续36个月以上工作经历；在海外取得博士学位且业绩特别突出的，可适当放宽工作年限要求；</p>\n<p>6.取得同行专家认可的科研或技术等成果，且具有成为该领域学术带头人或杰出人才的发展潜力；</p>\n<p>7.申请人尚未全职回国（来华）工作，或者2022年1月1日以后回国（来华）工作。获资助通知后须辞去海外工作或在海外无工作，全职回国（来华）工作不少于3年。</p>\n<p>（二）限项要求</p>\n<p>执行中央有关部门关于国家科技人才计划统筹衔接的要求。同层次国家科技人才计划只能承担一项，不能逆层次申请。</p>\n<p>四、政策支持<br />\n（一）专业申报指导</p>\n<p>1.权威专家指导，组织校级多轮申报指导；</p>\n<p>2.全程跟进协助，提供贴心服务。</p>\n<p>（二）优厚待遇支持</p>\n<p>1.国家自然科学基金委资助100—300万，期限3年。广东省按入选人才实际情况原则上不少于国家标准提供配套资助；同时入选人才可申报认定广州市高层次人才并享受100—500万元不等的购房补贴。</p>\n<p>2.薪酬福利待遇：</p>\n<p>（1）入选人才符合政策纳入广州市事业单位编制；</p>\n<p>（2）提供年薪60—150万元（税前）；</p>\n<p>（3）学校提供税前住房补贴额度100—250万元（与广州市政策对接），按政策协助申请广州市人才住房；</p>\n<p>（4）按学科基础和实际需要提供50—100万科研启动经费额度。提供研究生招生指标。提供实验空间、办公空间、支持组建创新科研团队等；</p>\n<p>（5）学校负责协调乙方子女就读九年制义务教育阶段学校的问题。</p>\n<p>五、申报说明<br />\n（一）申请人注意事项</p>\n<ol>\n<li>\n<p>符合条件的申请人，可按照项目指南要求，与依托单位签订工作合同或者意向性协议，于2023年1月15日以后登录信息系统，在线填写《国家自然科学基金优秀青年科学基金项目（海外）申请书》（以下简称申请书）。申请人对所提交申请材料的真实性负责。</p>\n</li>\n<li>\n<p>申请人应当按照申请书填报说明和撰写提纲的要求用简体中文或英文在线填写申请书，并上传附件等材料。</p>\n</li>\n<li>\n<p>优秀青年科学基金项目（海外）实行无纸化申请方式，申请人在线提交电子申请书及附件、公正性承诺书等材料至依托单位，由依托单位审核后在线提交自然科学基金委。</p>\n</li>\n</ol>\n<p>（二）依托单位注意事项</p>\n<ol>\n<li>依托单位应认真组织申请工作，并保证申请材料的真实性、完整性及合规性。</li>\n</ol>\n<p>2.依托单位应在规定的项目申请截止日期（2023年3月15日）前提交本单位电子申请书及附件材料。</p>\n<ol start=\"3\">\n<li>依托单位提交电子申请书时，应通过信息系统逐项确认，签署承诺后提交至自然科学基金委。</li>\n</ol>\n<p>六、联系方式<br />\n（一）广州航海学院人事处</p>\n<p>联系人：</p>\n<p>龚老师（18926258189）</p>\n<p>毕老师（18026393937）</p>\n<p>黄老师（13342816672）</p>\n<p>联系电话：020-31801689</p>\n<p>联系邮箱：hhrscsz@163.com</p>\n<p>（二）广州航海学院科研处</p>\n<p>联系人：</p>\n<p>缪老师（13533305092）</p>\n<p>曹老师（18617318980）</p>\n<p>温老师（13342816238）</p>\n<p>联系电话：020-32082163</p>\n<p>联系邮箱：gzhhxykyc@126.com</p>\n<p>七、相关链接<br />\n访问https://grants.nsfc.gov.cn/,点击“优秀青年科学基金项目（海外）依托单位系统”。### 三级标题</p>\n', '2023-03-14 12:56:54');
INSERT INTO `announcement` VALUES (43, '关于公布广州航海学院2022年公开招聘非事业编制心理咨询师  面试人员名单的通知', '<p>各位考生：</p>\n<p>根据《广州航海学院2022年公开招聘非事业编制心理咨询师公告》及相关规定，资格复审已完成，现将资格复审合格人员名单予以公布，详见附件。有关面试安排另行通知。</p>\n', '2023-03-14 12:58:53');
INSERT INTO `announcement` VALUES (44, '拟获得广州航海学院2021届优秀毕业生名单公示', '<p>本着公平、公开、公正的原则，根据广州航海学院优秀毕业生评选办法，现将拟获得学校2021届优秀毕业生名单予以公示，如下列同学有旷课、晚就寝时间内玩游戏、参与校园贷、多次晚归、未按疫情防控要求批准擅自离校等违法违纪情况，请在公示期内向学生处、教务处反映。</p>\n<p>公示期为：2021年6月23日到2021年6月27日</p>\n', '2023-03-14 12:59:45');
INSERT INTO `announcement` VALUES (45, '学校向全体师生员工致以国庆中秋节日的诚挚慰问', '<p>全体师生员工：</p>\n<p>在中华人民共和国成立71周年和中秋佳节双节到来之际，举国上下喜迎国庆，万紫千红欢度中秋，中共广州航海学院委员会向全体师生员工致以诚挚问候！祝愿大家国庆、中秋双节快乐！家庭幸福！学习（事业）进步！健康平安！</p>\n<p>习近平总书记在庆祝中华人民共和国成立70周年大会中的讲话鼓舞着全国各族人民奋勇前进：社会主义中国巍然屹立在世界东方，没有任何力量能够撼动我们伟大祖国的地位，没有任何力量能够阻挡中国人民和中华民族的前进步伐。</p>\n<p>广州航海学院全体师生员工将继续坚持在以习近平同志为核心的党中央坚强领导下，不断增强“四个意识”、坚定“四个自信”、做到“两个维护”，坚持立德树人，培养德智体美劳全面发展的中国特色社会主义建设者和接班人，为党育人、为国育才，为建设高水平应用型大学而努力奋斗！</p>\n<p>为了使全体师生员工度过一个欢乐祥和的节日，特向大家温馨提醒如下：</p>\n<p>一、务必注意安全。外出要特别注意交通、人身和财产安全，拒绝乘坐“五类车”，在外就餐要特别注意食品卫生安全，不到校外无证流动摊档消费，高度警惕“校园贷”陷阱，严防诈骗，拒绝参加宗教活动。</p>\n<p>二、疫情防控不松懈。外出请佩戴口罩，勤洗手、常通风，不聚集、保持安全距离。注意自身体温，有情况及时报告（辅导员、部门负责人）。</p>\n<p>三、实行报备制度。请全体师生员工严格落实“日报告”“零报告”制度，离穗前要向学校办理请假和行程报备手续，不得前往境外（含香港和台湾地区）和国内中高风险地区，尽量减少省外旅游探亲活动。</p>\n<p>四、全体学生于10月8日（星期四）下午18:00前返校，各二级学院组织晚点名，并将情况向学工处报备。</p>\n<p>中共广州航海学院委员会</p>\n<p>2020年9月28日</p>\n', '2023-03-14 13:00:40');
INSERT INTO `announcement` VALUES (46, '广东省高校网络招聘会-广州航海学院专场邀请函', '<p>尊敬的用人单位：</p>\n<p>衷心感谢贵单位多年来对我校就业工作的大力支持！</p>\n<p>在疫情防控期间，按照省教育厅的统一部署，我校暂停所有线下招聘活动，全面启动线上招聘活动。为切实做好我校2020届毕业生就业工作，定于2020年4月23日-5月19日举办“广州航海学院2020年春季省就业创业智慧服务平台网络招聘会”，特邀请贵单位届时参加，此次招聘活动不收取任何费用，现将有关事宜函告如下：</p>\n<p>一、主题</p>\n<p>广东省高校网络招聘会-广州航海学院专场</p>\n<p>二、指导单位</p>\n<p>广东省教育厅</p>\n<p>三、举办单位</p>\n<p>广州航海学院</p>\n<p>四、时间及地点</p>\n<p>开展时间：2020年4月23日—5月19日（同步开展）</p>\n<p>开展平台：广东省大学生就业创业智慧服务平台</p>\n<p>五、面向人群</p>\n<p>2020届应届毕业生共4543人，涵盖海运学院、轮机工程学院、船舶与海洋工程学院、港口与航运管理学院、航务工程学院、信息与通信工程学院、航运经贸学院、外语学院、艺术设计9个学院30多个专业。</p>\n<p>六、参会方式</p>\n<p>登陆广东省大学生就业创业智慧服务平台注册https://job.gdedu.gov.cn/comp/#/login，邀请码可为空，网页注册后可登陆微信小程序“广东大学生就业创业”；</p>\n<p>注册说明点击链接</p>\n<p>https://job.gdedu.gov.cn/help/CompanyOperationManual.pdf；</p>\n<p>省就业指导中心审核注册用人单位的真实性及岗位的合法性；</p>\n<p>用人单位选择广州航海学院2020年春季就业智慧平台网络招聘会并将岗位加入；</p>\n<p>我校对参会用人单位进行审核；</p>\n<p>省就业指导中心将针对用人单位发布的职位针对本校学生进行岗位匹配推送；</p>\n<p>用人单位查看投递简历选择毕业生邀约面试（疫情期间线上面试），签订就业协议书；</p>\n<p>请用人单位与毕业生在国家和省毕业生就业方针政策范围内进行双向选择，不得以任何方式收取应聘学生的任何费用，不能泄露毕业生资料等及非正常合理招聘活动。</p>\n<p>七、联系方式</p>\n<p>广航毕业生就业中心</p>\n<p>就业信息网：</p>\n<p>http://jyzdzx.gzmtu.edu.cn/index.htm</p>\n<p>电话：020-32083039</p>\n<p>邮箱：1324908581@qq.com</p>\n<p>在线QQ：1324908581</p>\n', '2023-03-14 13:01:41');
INSERT INTO `announcement` VALUES (47, '【系统维护公告】系统设计完毕', '<p>本系统1.0版本已设计完毕，进入维护阶段<br />\n开发者：13168162412（微信同号）</p>\n', '2023-03-14 13:03:58');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `article_author_id` int NULL DEFAULT NULL COMMENT '作者id',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目',
  `article_publish_date` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `article_digest` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '摘要',
  `article_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `article_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `article_page_view` int NULL DEFAULT 0 COMMENT '访问量',
  `article_like_num` int NULL DEFAULT 0 COMMENT '点赞量',
  `article_trample_num` int NULL DEFAULT 0 COMMENT '点踩量',
  `article_comment_num` int NULL DEFAULT 0 COMMENT '评论数',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (5, 16, '教程：使用Jmeter对带token的接口进行压测', '2023-03-09 22:47:22', '【教程】使用Jmeter对带token的接口进行压测', '<p>最近在研究并发，用到了Jmeter对接口进行压力测试，记录下使用过程</p>\n<h3><a id=\"_binJmeterproperties_2\"></a><strong>一. 配置/bin下的Jmeter.properties，打开以下两项配置，一个是默认的编码，一个是默认的语言</strong></h3>\n<p><img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224236.png\" alt=\"微信截图_20230309224236.png\" /><br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224350.png\" alt=\"微信截图_20230309224350.png\" /></p>\n<h3><a id=\"_jmeterbat_7\"></a><strong>二. 打开jmeter.bat运行，新建线程组，在线程组下新建以下内容</strong></h3>\n<p><img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224409.png\" alt=\"微信截图_20230309224409.png\" /></p>\n<p><strong>1. CSV数据文件设置（右键添加，在配置元件目录中）</strong></p>\n<p>首先自行调用登录接口生成不同用户的token，然后保存到一个txt文件中，如下<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224431.png\" alt=\"微信截图_20230309224431.png\" /></p>\n<p>然后配置CSV<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224445.png\" alt=\"微信截图_20230309224445.png\" /></p>\n<p><strong>2. HTTP请求默认值（右键添加，在配置元件目录中）</strong></p>\n<p>协议设置为HTTP，服务器名称或IP设置为localhost，端口号设置为你的服务器端口号（此处为8088）<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224501.png\" alt=\"微信截图_20230309224501.png\" /></p>\n<p><strong>3. HTTP信息头管理器（右键添加，在配置元件目录中）</strong></p>\n<p>添加键值对，名称为Authorization，值为${token}，即之前在CSV配置中设置的变量名<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224525.png\" alt=\"微信截图_20230309224525.png\" /></p>\n<p><strong>4. 添加测试接口（右键添加，取样器–&gt;HTTP请求）</strong></p>\n<p>配置你需要测试的接口的请求方法、路径，以及接口所需的参数<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224539.png\" alt=\"微信截图_20230309224539.png\" /></p>\n<p><strong>5. 添加多种监听器（结果树、聚合报告）</strong></p>\n<h3><a id=\"_36\"></a><strong>三.配置线程组</strong></h3>\n<p>线程数是测试时创建的线程数量，线程数1000加上循环次数10，相当于10000个线程同时访问这个接口<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224554.png\" alt=\"微信截图_20230309224554.png\" /></p>\n<h3><a id=\"_39\"></a><strong>四.启动，查看聚合报告</strong></h3>\n<p><img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309224617.png\" alt=\"微信截图_20230309224617.png\" /><br />\n吞吐量很低，原因还没有找到-。-</p>\n', '[\"Jmeter\",\"压测\",\"教程\"]', 34, 5, 1, 3);
INSERT INTO `article` VALUES (6, 17, '【分享】和朋友们玩真的好高兴', '2023-03-09 23:17:11', '发些最近的日常！又要开始计划出行了🍂', '<p>发些最近的日常！又要开始计划出行了🍂<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309231246.png\" alt=\"微信截图_20230309231246.png\" /></p>\n', '[\"日常\",\"朋友\",\"出游\"]', 19, 3, 0, 3);
INSERT INTO `article` VALUES (7, 18, '【影视】高启强一年三连跳，真的是因为一本《孙子兵法》吗？', '2023-03-09 23:34:15', '大家好，我是花花真世界，热播剧《狂飙》已经结束了，但是关于该剧的网络热度却是丝毫不减，甚至有些更甚热播之时，其中核心人物高启强的话题热度更是居高不下...', '<ul>\n<li>\n<p>大家好，我是花花真世界，热播剧《狂飙》已经结束了，但是关于该剧的网络热度却是丝毫不减，甚至有些更甚热播之时，其中核心人物高启强的话题热度更是居高不下，很多人疑问高启强能在一年内身份完成三级跳跃，真的只是因为一本《孙子兵法》嘛？答案当然不是，下面就让小编带你一起扒拉下高启强身份三连跳的真实原因。<br />\n<img src=\"http://localhost:8088/resources/ed0ddf587e960a19a67520650fd334213037e467.jpg@942w_530h_progressive.jpg\" alt=\"ed0ddf587e960a19a67520650fd334213037e467.jpg942w_530h_progressive.jpg\" /></p>\n</li>\n<li>\n<p>首先孙子兵法的作用不可置否，高启强出身一般，为了弟弟妹妹放弃了自己的学业，所以文化水平一直是他的短板之一，自从安欣给他推荐了几本读物之后，尤其是孙子兵法，高启强一直从中汲取相关知识，以此弥补自己的短板，在与程程两者之间的明争暗斗之中，高启强的表现可谓是高光时刻，先是一招将计就计，而后自己假装进入监狱，随后一招釜底抽薪，让建工集团直接停止运转，可谓“孙子兵法学者”的绰号名副其实。<br />\n<img src=\"http://localhost:8088/resources/c0182ad92ff238585438c06b57166c5226f36b8b.jpg@942w_629h_progressive.jpg\" alt=\"c0182ad92ff238585438c06b57166c5226f36b8b.jpg942w_629h_progressive.jpg\" /></p>\n</li>\n<li>\n<p>第二点则是高启强天生具备的素养，那便是一双伯乐慧眼，高启强与刘邦一样，自身硬实力并不算出众，但是却懂得赏识人才，只是一眼，就看中了老默，并迅速着手将老默收归己用，在除掉徐江这件事上，再次证明了高启强的眼光之辣。<br />\n<img src=\"http://localhost:8088/resources/b89c433527436823da54a7df2bccb76bc032da89.jpg@942w_530h_progressive.jpg\" alt=\"b89c433527436823da54a7df2bccb76bc032da89.jpg942w_530h_progressive.jpg\" /></p>\n</li>\n<li>\n<p>最后一点也是最重要的，那便是高启强的能屈能伸的坚毅品质，初为鱼贩之时，面对唐家兄弟的打压和欺辱，高启强隐忍不言，但却是在暗中积蓄力量；当遇见陈书婷之后，成功见到了京海黑恶势力幕后大佬泰叔，当众下跪认陈泰为干爹，丝毫没有任何犹豫，可谓是能屈之最；在程程被抓之后，高启强丝毫没有留手，直接迫使泰叔让位，成功接手建工集团，为强盛集团的崛起打好了地基；面对压迫和屈辱，高启强隐忍不言，当发现时机，高启强不鸣则已一鸣惊人。</p>\n</li>\n</ul>\n<p>本期内容到此结束，欢迎大家留言补充，我是花花真世界，我们下期再见。 作者：花花真世界 https://www.bilibili.com/read/cv22302821?from=category_28 出处：bilibili</p>\n', '[\"影视\",\"高启强\",\"孙子兵法\"]', 17, 4, 0, 3);
INSERT INTO `article` VALUES (8, 19, 'C++map容器遍历删除:cannot increment value-initialized map/set iterator', '2023-03-09 23:50:46', '今天写C++结课作业遇到一个遍历删除的bug：\n原代码：\n```c++\nfor(it=imap.begin();it!=imap.end();it++)\nimap.erase(it)\n```\n运行后：', '<p>2020-06-13 02:27:44<br />\n今天写C++结课作业遇到一个遍历删除的bug：<br />\n原代码：</p>\n<pre><div class=\"hljs\"><code class=\"lang-c++\"><span class=\"hljs-keyword\">for</span>(it=imap.<span class=\"hljs-built_in\">begin</span>();it!=imap.<span class=\"hljs-built_in\">end</span>();it++)\nimap.<span class=\"hljs-built_in\">erase</span>(it)\n</code></div></pre>\n<p>运行后：<br />\n<img src=\"http://localhost:8088/resources/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230309234901.png\" alt=\"微信截图_20230309234901.png\" /></p>\n<p>第一次循环末尾，it的位置被erase破坏，失去了原本结构，固不能做++。<br />\n若把erase和++ 放到一起，则此时的it会预先缓存，可以安全完成++操作。<br />\n修改如下：</p>\n<pre><div class=\"hljs\"><code class=\"lang-C++\"><span class=\"hljs-keyword\">for</span>(it=imap.<span class=\"hljs-built_in\">begin</span>();it!=imap.<span class=\"hljs-built_in\">end</span>();)\n{\n    <span class=\"hljs-keyword\">if</span>((*it).second.<span class=\"hljs-built_in\">find_age</span>())\n        imap.<span class=\"hljs-built_in\">earse</span>(it++);\n    <span class=\"hljs-keyword\">else</span> <span class=\"hljs-keyword\">break</span>;\n}\n</code></div></pre>\n<p>因为我的map容器的键值存放的是对象，然后我目前只想到用其函数返回值来判断此对象是否存在。</p>\n<p>过几天我会把我C++的结课作业发出来，请大家多多指教。<br />\n————————————————<br />\n版权声明：本文为CSDN博主「莉妮可丝的猫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。<br />\n原文链接：https://blog.csdn.net/weixin_46523794/article/details/106727030</p>\n', '[\"C++\",\"map容器\",\"遍历删除\"]', 12, 0, 0, 3);
INSERT INTO `article` VALUES (9, 27, 'mavon-editor使用(Element)', '2023-03-11 00:04:52', '安装mavon-editor\n\n```\nnpm install mavon-editor --save\n```\n\n##### main.js\n\n```javascript\n/* 引入 */\nimpor', '<h5><a id=\"mavoneditor_0\"></a>安装mavon-editor</h5>\n<pre><code class=\"lang-\">npm install mavon-editor --save\n</code></pre>\n<h5><a id=\"mainjs_6\"></a>main.js</h5>\n<pre><div class=\"hljs\"><code class=\"lang-javascript\"><span class=\"hljs-comment\">/* 引入 */</span>\n<span class=\"hljs-keyword\">import</span> mavonEditor <span class=\"hljs-keyword\">from</span> <span class=\"hljs-string\">&#x27;mavon-editor&#x27;</span>\n<span class=\"hljs-keyword\">import</span> <span class=\"hljs-string\">&#x27;mavon-editor/dist/css/index.css&#x27;</span>\n\n<span class=\"hljs-comment\">/* 挂载 */</span>\n<span class=\"hljs-title class_\">Vue</span>.<span class=\"hljs-title function_\">use</span>(mavonEditor)\n</code></div></pre>\n<h5><a id=\"vue_17\"></a>vue</h5>\n<pre><div class=\"hljs\"><code class=\"lang-js\">&lt;template&gt;\n	&lt;!-- mavon-editor --&gt;\n    <span class=\"language-xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">mavon-editor</span> @<span class=\"hljs-attr\">change</span>=<span class=\"hljs-string\">&quot;getHtml&quot;</span> <span class=\"hljs-attr\">:toolbars</span>=<span class=\"hljs-string\">&quot;toolbars&quot;</span> <span class=\"hljs-attr\">ref</span>=<span class=\"hljs-string\">&quot;md&quot;</span> <span class=\"hljs-attr\">class</span>=<span class=\"hljs-string\">&quot;me&quot;</span>\n                <span class=\"hljs-attr\">placeholder</span>=<span class=\"hljs-string\">&quot;畅所欲言吧...&quot;</span> <span class=\"hljs-attr\">fontSize</span>=<span class=\"hljs-string\">&quot;18px&quot;</span> <span class=\"hljs-attr\">:html</span>=<span class=\"hljs-string\">&quot;true&quot;</span> <span class=\"hljs-attr\">toolbarsBackground</span>=<span class=\"hljs-string\">&quot;#fff&quot;</span>&gt;</span>\n    <span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">mavon-editor</span>&gt;</span></span>\n	&lt;!-- 内容展示用v-html即可 --&gt;\n    <span class=\"language-xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">div</span> <span class=\"hljs-attr\">class</span>=<span class=\"hljs-string\">&quot;content&quot;</span> <span class=\"hljs-attr\">v-html</span>=<span class=\"hljs-string\">&quot;content&quot;</span>&gt;</span><span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">div</span>&gt;</span></span>\n&lt;/template&gt;\n\n<span class=\"language-xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">script</span>&gt;</span><span class=\"language-javascript\">\n<span class=\"hljs-keyword\">export</span> <span class=\"hljs-keyword\">default</span> {\n    <span class=\"hljs-attr\">data</span>: <span class=\"hljs-function\">() =&gt;</span> ({\n        <span class=\"hljs-attr\">content</span>:<span class=\"hljs-string\">&#x27;&#x27;</span>,\n		<span class=\"hljs-comment\">/* 工具栏设置 */</span>\n        <span class=\"hljs-attr\">toolbars</span>: {\n            <span class=\"hljs-attr\">bold</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 粗体</span>\n            <span class=\"hljs-attr\">italic</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 斜体</span>\n            <span class=\"hljs-attr\">header</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 标题</span>\n            <span class=\"hljs-attr\">underline</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 下划线</span>\n            <span class=\"hljs-attr\">strikethrough</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 中划线</span>\n            <span class=\"hljs-attr\">mark</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 标记</span>\n            <span class=\"hljs-attr\">superscript</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 上角标</span>\n            <span class=\"hljs-attr\">subscript</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 下角标</span>\n            <span class=\"hljs-attr\">quote</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 引用</span>\n            <span class=\"hljs-attr\">ol</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 有序列表</span>\n            <span class=\"hljs-attr\">ul</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 无序列表</span>\n            <span class=\"hljs-attr\">link</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 链接</span>\n            <span class=\"hljs-attr\">imagelink</span>: <span class=\"hljs-literal\">false</span>, <span class=\"hljs-comment\">// 图片链接</span>\n            <span class=\"hljs-attr\">code</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// code</span>\n            <span class=\"hljs-attr\">table</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 表格</span>\n            <span class=\"hljs-attr\">fullscreen</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 全屏编辑</span>\n            <span class=\"hljs-attr\">readmodel</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 沉浸式阅读</span>\n            <span class=\"hljs-attr\">htmlcode</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 展示html源码</span>\n            <span class=\"hljs-attr\">help</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 帮助</span>\n            <span class=\"hljs-comment\">/* 1.3.5 */</span>\n            <span class=\"hljs-attr\">undo</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 上一步</span>\n            <span class=\"hljs-attr\">redo</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 下一步</span>\n            <span class=\"hljs-attr\">trash</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 清空</span>\n            <span class=\"hljs-attr\">save</span>: <span class=\"hljs-literal\">false</span>, <span class=\"hljs-comment\">// 保存（触发events中的save事件）</span>\n            <span class=\"hljs-comment\">/* 1.4.2 */</span>\n            <span class=\"hljs-attr\">navigation</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 导航目录</span>\n            <span class=\"hljs-comment\">/* 2.1.8 */</span>\n            <span class=\"hljs-attr\">alignleft</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 左对齐</span>\n            <span class=\"hljs-attr\">aligncenter</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 居中</span>\n            <span class=\"hljs-attr\">alignright</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 右对齐</span>\n            <span class=\"hljs-comment\">/* 2.2.1 */</span>\n            <span class=\"hljs-attr\">subfield</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 单双栏模式</span>\n            <span class=\"hljs-attr\">preview</span>: <span class=\"hljs-literal\">true</span>, <span class=\"hljs-comment\">// 预览</span>\n        },\n	}),\n	<span class=\"hljs-attr\">methods</span>:{\n		<span class=\"hljs-comment\">/* 获取解析后的html */</span>\n        <span class=\"hljs-title function_\">getHtml</span>(<span class=\"hljs-params\">value, render</span>) {\n            <span class=\"hljs-comment\">// 直接使用content作为文章内容存储数据库，获取时无需解析</span>\n            <span class=\"hljs-variable language_\">this</span>.<span class=\"hljs-property\">content</span> = render\n        },\n	}\n}\n</span><span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">script</span>&gt;</span></span>\n</code></div></pre>\n', '[\"md编辑器\",\"element\"]', 16, 2, 0, 2);
INSERT INTO `article` VALUES (11, 28, ' 面试题｜3月12日拼多多春招笔试第三题-开始考网络流了吗？？', '2023-03-14 13:12:47', '题目内容\n又到了团建的时间，多多君负责安排这次的团建活动。多多君准备了三个活动（分别编号 ', '<p>来源<br />\n感谢某群友的投稿. 更多大厂真题以及题解关注&quot;塔子哥学算法&quot;</p>\n<p>题目内容<br />\n又到了团建的时间，多多君负责安排这次的团建活动。多多君准备了三个活动（分别编号<br />\n�<br />\nA<br />\n�<br />\nB 和<br />\n�<br />\nC ），每个活动分别有人数上限以及每个人参加的费用。</p>\n<p>参加团建的有<br />\n�<br />\nN 个人(分别编号<br />\n1<br />\n1 ~<br />\n�<br />\nN )，每个人先投票选择若干个意向的活动，最终每个人只能参加其中一个。</p>\n<p>多多君收集完投票结果后，发现如何安排成为了大难题：如何在满足所有人的意向的情况下，使得活动的总费用最少。</p>\n<p>于是多多君找到了擅长编程的你，希望你能帮助找到一个合理的团建计划。</p>\n<p>输入描述<br />\n第一行，一个整数<br />\n�<br />\nN ，代表准备参加活动的人数。（<br />\n1<br />\n≤<br />\n�<br />\n≤<br />\n100<br />\n1≤N≤100 ）</p>\n<p>接下来<br />\n�<br />\nN 行，每行一个由 “ABC” 组成的字符串，其中第<br />\n�<br />\ni 行表示第<br />\n�<br />\ni 个人投票了哪几个活动。（输入保证字符串非空，且由大写的 “ABC” 字符组成）</p>\n<p>最后<br />\n3<br />\n3 行，每行两个整数，分别表示三个活动的人数上限以及每个人参加的费用。（人数上限以及参与活动的费用均为不超过<br />\n100<br />\n100 的正整数）</p>\n<p>输出描述<br />\n输出共<br />\n2<br />\n2 行</p>\n<p>如果能满足所有人的要求，第一行输出 “YES” ，第二行输出最少的总费用。</p>\n<p>如果不能满足所有人的要求，第一行输出 “NO” ，第二行输出最多能满足多少人。</p>\n<p>样例<br />\n样例一<br />\n输入</p>\n<p>5<br />\nA<br />\nB<br />\nC<br />\nAB<br />\nABC<br />\n2 1<br />\n2 2<br />\n2 3<br />\n输出</p>\n<p>YES<br />\n9<br />\n样例解释</p>\n<p>可以满足所有人的要求，其中一种费用最少的方案:</p>\n<p>A：{<br />\n1<br />\n，<br />\n4<br />\n1，4 }</p>\n<p>B：{<br />\n2<br />\n，<br />\n5<br />\n2，5 }</p>\n<p>C：{<br />\n3<br />\n3 }</p>\n<h1><a id=\"\n1\n\n2\n\n2\n\n2\n\n3\n\n1_97\"></a>总共需要费用：<br />\n1<br />\n∗<br />\n2<br />\n+<br />\n2<br />\n∗<br />\n2<br />\n+<br />\n3<br />\n∗<br />\n1</h1>\n<p>9<br />\n1∗2+2∗2+3∗1=9</p>\n<p>样例二<br />\n输入</p>\n<p>5<br />\nA<br />\nB<br />\nC<br />\nAB<br />\nAB<br />\n1 1<br />\n2 2<br />\n3 3<br />\n输出</p>\n<p>NO<br />\n4<br />\n样例解释</p>\n<p>无法满足所有人的需求，其中一种满足最多人的方案：<br />\nA：{<br />\n1<br />\n1 }<br />\nB：{<br />\n2<br />\n，<br />\n4<br />\n2，4 }<br />\n3：{<br />\n3<br />\n3 }<br />\n共<br />\n4<br />\n4 人</p>\n<p>作者：塔子<br />\n链接：https://leetcode.cn/circle/discuss/vWp6xX/<br />\n来源：力扣（LeetCode）<br />\n著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</p>\n', '[\"面试题\",\"面试\"]', 250, 0, 0, 0);
INSERT INTO `article` VALUES (12, 34, ' 问一个算法题', '2023-03-14 13:13:57', '\n问一个算法题', '<p>有hell,well,hello,world等字符串组，现在问能否拼接成helloword</p>\n<p>求有没有大佬实现一下，数量级未知，一个老的面试题。</p>\n', '[\"问答\",\"Q&A\"]', 300, 0, 0, 0);
INSERT INTO `article` VALUES (13, 36, '一道bfs的题目，走迷宫，地图带钥匙和锁，C++能过，python超时，求大佬看看', '2023-03-14 13:15:16', '题目是：\n假设一个探险家被困在了地底的迷宫之中，要从当前位置开始找到一条通往迷宫出口的路径。迷宫可以用一个二维矩阵组成，有的部分是墙，有的部分是路。迷宫之中有的路上还有门，每扇门都在迷宫的某个地方有与', '<p>https://www.nowcoder.com/practice/e3fc4f8094964a589735d640424b6a47<br />\n题目是：<br />\n假设一个探险家被困在了地底的迷宫之中，要从当前位置开始找到一条通往迷宫出口的路径。迷宫可以用一个二维矩阵组成，有的部分是墙，有的部分是路。迷宫之中有的路上还有门，每扇门都在迷宫的某个地方有与之匹配的钥匙，只有先拿到钥匙才能打开门。请设计一个算法，帮助探险家找到脱困的最短路径。如前所述，迷宫是通过一个二维矩阵表示的，每个元素的值的含义如下 0-墙，1-路，2-探险家的起始位置，3-迷宫的出口，大写字母-门，小写字母-对应大写字母所代表的门的钥匙<br />\n输入描述：<br />\n迷宫的地图，用二维矩阵表示。第一行是表示矩阵的行数和列数M和N 后面的M行是矩阵的数据，每一行对应与矩阵的一行（中间没有空格）。M和N都不超过100, 门不超过10扇。<br />\n输出描述：<br />\n路径的长度，是一个整数</p>\n<p>示例1<br />\n输入：<br />\n5 5<br />\n02111<br />\n01a0A<br />\n01003<br />\n01001<br />\n01111<br />\n输出：<br />\n7</p>\n<p>作者：islch<br />\n链接：https://leetcode.cn/circle/discuss/Cfhz3w/<br />\n来源：力扣（LeetCode）<br />\n著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</p>\n', '[\"C++\",\"python\",\"bfs\"]', 1042, 0, 0, 0);
INSERT INTO `article` VALUES (14, 33, ' 刚刚做的笔试编程题，不会做，请大家帮帮忙，用C语言', '2023-03-14 13:16:10', '有一个字符串，需要做一些修改。具体的，字符串里的任意位置的字符修改为任意的数字字符。问题是需要多少次修改，可以使得修改后的字符串不包含两个连续相同的字符？\n', '<p>用C语言！！！</p>\n<p>题目是</p>\n<p>有一个字符串，需要做一些修改。具体的，字符串里的任意位置的字符修改为任意的数字字符。问题是需要多少次修改，可以使得修改后的字符串不包含两个连续相同的字符？</p>\n<p>例如，对于字符串&quot;111222333&quot;, 可以进行3次修改变为&quot;121212313&quot;</p>\n<p>输入描述：</p>\n<p>一行，一个字符串s，保证s只包含数字字符。</p>\n<p>1&lt;=|s|&lt;=100000</p>\n<p>输出描述：</p>\n<p>一行，一个整数，表示修改的最少次数。</p>\n<p>示例1：</p>\n<p>输入 字符串 111222333</p>\n<p>输出 整数 3</p>\n<p>示例2：</p>\n<p>输入 字符串 11551111</p>\n<p>输出 整数 4</p>\n<p>作者：Vibrant AlmeidaMeI<br />\n链接：https://leetcode.cn/circle/discuss/eiLwOf/<br />\n来源：力扣（LeetCode）<br />\n著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</p>\n', '[\"C语言\",\"字符串\",\"笔试\"]', 202, 0, 0, 0);

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `to_article_id` int NULL DEFAULT NULL COMMENT '被评论的帖子id',
  `to_comment_id` int NULL DEFAULT -1 COMMENT '被评论的评论的id',
  `from_user_id` int NULL DEFAULT NULL COMMENT '评论人id',
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `comment_create_time` timestamp NULL DEFAULT NULL COMMENT '时间',
  `comment_like_num` int NULL DEFAULT 0 COMMENT '点赞数量',
  `comment_trample_num` int NULL DEFAULT 0 COMMENT '点踩数量',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `fk_to_article_id`(`to_article_id` ASC) USING BTREE,
  CONSTRAINT `fk_to_article_id` FOREIGN KEY (`to_article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_comment
-- ----------------------------
INSERT INTO `article_comment` VALUES (5, 5, -1, 16, '好厉害！真的，可以放图片了！！！😉', '2023-03-09 22:57:48', 0, 0);
INSERT INTO `article_comment` VALUES (6, 6, -1, 18, '第一张！巨有氛围感！谁懂！谁懂！！', '2023-03-09 23:19:35', 0, 0);
INSERT INTO `article_comment` VALUES (7, 6, 6, 25, '回复 @米老师 什么叫决定性瞬间[妙啊]', '2023-03-09 23:20:43', 0, 0);
INSERT INTO `article_comment` VALUES (8, 6, -1, 19, 'P3中间的朋友笑容凝固🤡😳', '2023-03-09 23:22:00', 0, 0);
INSERT INTO `article_comment` VALUES (9, 7, -1, 20, '笑死，在竞赛平台发高启强，等下老默晚上找你送鱼😨😨', '2023-03-09 23:36:38', 8, 0);
INSERT INTO `article_comment` VALUES (10, 7, 9, 17, '回复 @卉卉12138 我裂开了，怎么说', '2023-03-09 23:37:37', 2, 1);
INSERT INTO `article_comment` VALUES (11, 7, -1, 23, '真有你的强哥🤡', '2023-03-09 23:38:24', 2, 0);
INSERT INTO `article_comment` VALUES (12, 8, -1, 17, '毕业快乐！😄😄', '2023-03-09 23:51:29', 5, 0);
INSERT INTO `article_comment` VALUES (13, 8, -1, 22, '😘很好的方法 非常感谢', '2023-03-09 23:53:20', 3, 0);
INSERT INTO `article_comment` VALUES (15, 9, -1, 16, '123😍', '2023-03-12 21:46:08', 3, 0);
INSERT INTO `article_comment` VALUES (16, 9, 15, 16, '回复 @MZY123 123', '2023-03-12 21:46:15', 0, 0);
INSERT INTO `article_comment` VALUES (17, 5, -1, 16, '试试路径导航', '2023-03-13 21:37:46', 0, 0);
INSERT INTO `article_comment` VALUES (18, 5, 17, 16, '回复 @MZY123 二级', '2023-03-13 21:38:24', 0, 0);

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `competition_id` int NOT NULL AUTO_INCREMENT COMMENT '竞赛id',
  `competition_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `competition_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图片',
  `competition_type` tinyint NULL DEFAULT 0 COMMENT '类型，0工科竞赛，1文科竞赛',
  `competition_joined_is_open` tinyint NULL DEFAULT NULL COMMENT '报名开关 0关闭 1打开',
  `competition_file_upload_is_open` tinyint NULL DEFAULT 0 COMMENT '文件上传开关，0关闭，1打开',
  `competition_status` tinyint NULL DEFAULT 1 COMMENT '状态，0已结束，1进行中',
  `competition_stage` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '步骤（JSON化的步骤对象的数组）',
  `competition_information_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容（JSON化的内容对象的数组）',
  `is_team_up` tinyint NULL DEFAULT 0 COMMENT '是否需要组队报名，0个人，1组队',
  `sign_up_min_team_member_num` int NULL DEFAULT NULL COMMENT '队伍最少人数',
  `sign_up_max_team_member_num` int NULL DEFAULT NULL COMMENT '队伍最多人数',
  `competition_audience` tinyint NULL DEFAULT 0 COMMENT '受众，0面向全校，1面向学院、2面向专业、3面向个人',
  `competition_audience_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体受众',
  `competition_joined_num` int NULL DEFAULT 0 COMMENT '已报名数量',
  `competition_stage_now` tinyint NULL DEFAULT 0 COMMENT '目前进行的步骤',
  `competition_undertaker` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '承办方名称',
  `competition_undertaker2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校内承办单位名称',
  `competition_undertaker3_id` int NULL DEFAULT NULL COMMENT '竞赛发布、负责人id',
  `competition_advisor_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '指导老师信息',
  `competition_begin_time` timestamp NULL DEFAULT NULL COMMENT '竞赛开始时间',
  `competition_end_time` timestamp NULL DEFAULT NULL COMMENT '竞赛结束时间',
  `competition_extra_points` int NULL DEFAULT NULL COMMENT '预计综测加分',
  `competition_publish_date` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`competition_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES (56, '天堂硅谷·数字经济算法编程大赛', 'http://localhost:8088/resources/competition_56.jpg', 0, 1, 0, 1, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"【工作机会奖励】\",\"infoContent\":\"排名第 1 - 500 名的参赛者可获得「杭州市高新区（滨江）」辖区内企业校招和实习生岗位招聘的内推机会。\"},{\"infoTitle\":\"【实物奖励】\",\"infoContent\":\"排名第 1 名的参赛者可获海康威视提供的「智能家居礼包（CB2 迷你摄像机+ DP2C 智能猫眼）」x1\\n排名第 2 - 3 名的参赛者可获吉利控股提供的「20 寸行李箱」× 1\\n排名第 4 - 6 名的参赛者可获恒生电子提供的「电煮锅 + 飞盘 + 钥匙扣」 × 1\\n排名第 7 - 16 名的参赛者可获大华股份提供的「定制运动水壶」× 1\\n排名第 17 - 36 名的参赛者可获电魂网络提供的「像素手办」× 1\\n排名第 66 名的参赛者可获零跑汽车提供的「零跑 C11 1:24 合金车模」× 1\"}]', 0, 0, 0, 1, '信息与通信工程学院', 1, 2, '杭州高新技术产业开发区（滨江）& 力扣', '信息与通信工程学院', 16, '黄老师 13124152423', '2023-01-01 00:00:00', '2023-06-11 00:00:00', 8, '2023-03-09 17:29:17');
INSERT INTO `competition` VALUES (57, '中国银联专场竞赛（2023届校园招聘专场）', 'http://localhost:8088/resources/competition_57.jpg', 0, 0, 1, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"【工作机会奖励】\",\"infoContent\":\"排名第 1 - 500 名的参赛者可获「中国银联」校招/社招/实习简历内推机会。\"},{\"infoTitle\":\"【实物奖励】\",\"infoContent\":\"排名第 1 - 10 名的参赛者可获中国银联提供「充电宝+保温杯+笔记本」 × 1\\n排名第 11 - 20 名的参赛者可获中国银联提供「充电宝+笔记本」 × 1\\n排名第 21 - 30 名的参赛者可获中国银联「保温杯+数据线磁吸」 × 1\\n排名第 31 - 40 名的参赛者可获中国银联提供「帆布袋+数据线磁吸」 × 1\\n排名第 41 - 50 名的参赛者可获「帆布包」× 1\"}]', 1, 4, 8, 1, '信息与通信工程学院', 0, 2, '「中国银联 & 力扣」', '信息与通信工程学院', 16, '黄老师 13216213251', '2023-01-01 00:00:00', '2023-06-11 00:00:00', 8, '2023-03-09 18:37:22');
INSERT INTO `competition` VALUES (58, 'AutoX 安途智行专场竞赛', 'http://localhost:8088/resources/competition_58.jpg', 0, 1, 0, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"【工作机会奖励】\",\"infoContent\":\"排名第 1 - 500 名的参赛者「AutoX 安途智行」简历内推机会（竞赛表现突出的参赛者可获 AutoX 安途智行面试直通卡）。\\n内推岗位适用范围： 感知算法工程师/行为预测算法工程师/深度学习算法工程师/行为决策算法工程师/路径规划算法工程师/机器学习算法工程师/视觉算法工程师/定位算法工程师/C++工程师（自动驾驶仿真平台、车载基础架构、数据平台、高精地图）\"},{\"infoTitle\":\"【实物奖励】\",\"infoContent\":\"排名第 1 - 3 名的参赛者可获 AutoX 安途智行提供「樱桃 Cheery MX 1.0 宝可梦」 × 1\\n排名第 4 - 10 名的参赛者可获 AutoX 安途智行提供 「定制背包」× 1\\n排名第 11 - 20 名的参赛者可获 AutoX 安途智行提供 「定制咖啡杯」× 1\"}]', 1, 2, 6, 1, '信息与通信工程学院', 0, 2, '「AutoX 安途智行 & 力扣」', '信息与通信工程学院', 16, '黄老师 13168215201', '2023-03-04 00:00:00', '2023-04-11 00:00:00', 8, '2023-03-10 23:48:15');
INSERT INTO `competition` VALUES (59, '九坤投资专场竞赛', 'http://localhost:8088/resources/competition_59.jpg', 0, 1, 0, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"【工作机会奖励】\",\"infoContent\":\"排名第 1 - 10 名的参赛者「九坤投资」校招/实习免笔试初筛的机会。\\n排名第 1 - 100 名的参赛者「九坤投资」校招/社招/实习简历专属内推机会。\"},{\"infoTitle\":\"【实物奖励】\",\"infoContent\":\"排名第 1 - 10 名的参赛者可获九坤投资提供「行李箱 + 创意贴纸 +定制T恤」 × 1\\n排名第 11 - 20 名的参赛者可获九坤投资提供 「帆布包 + 运动礼盒」× 1\\n排名第 21 - 30 名的参赛者九坤投资提供「邮差包 + 创意便利贴 + 定制T恤」 × 1\\n排名第 31 - 40 名的参赛者九坤投资提供 「帆布包 + 指玩模方 + 数据线」× 1\\n排名第 41 - 50 名的参赛者九坤投资提供 「帆布包 + 指玩模方 + 数据线」× 1\"}]', 1, 2, 8, 0, '', 0, 2, '「九坤投资 & 力扣」', '信息与通信工程学院', 16, '李老师 15013362422', '2023-03-17 00:00:00', '2023-03-25 00:00:00', 5, '2023-03-10 23:51:18');
INSERT INTO `competition` VALUES (60, '2022年度杭州未来科技城数字经济人才编程大赛', 'http://localhost:8088/resources/competition_60.jpg', 0, 1, 1, 1, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 1, '信息与通信工程学院', 0, 2, '「浙江杭州未来科技城 & 力扣」', '信息与通信工程学院', 16, '黄老师 13015242012', '2023-03-25 00:00:00', '2023-04-25 00:00:00', 5, '2023-03-10 23:53:23');
INSERT INTO `competition` VALUES (61, '顺丰科技智慧物流校园技术挑战赛', 'http://localhost:8088/resources/competition_61.jpg', 0, 1, 0, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"【工作机会奖励】\",\"infoContent\":\"排名第 1 - 10 名的参赛者可获顺丰科技校招提前批终面直通卡 x 1\\n报名本场竞赛可获顺丰科技提供「简历 PASS 卡 + 大咖线上微课」x1\"},{\"infoTitle\":\"【实物奖励】\",\"infoContent\":\"排名第 1 名的参赛者可获顺丰科技提供的人才奖金 30,000 元\\n排名第 2 - 3 名的参赛者可获顺丰科技提供的人才奖金 20,000 元\\n排名第 4 - 6 名的参赛者可获顺丰科技提供的人才奖金 10,000 元\\n排名第 7 - 10 名的参赛者可获顺丰科技提供的人才奖金 5,000 元\\n排名第 11 - 20 名的参赛者可获顺丰科技提供的人才奖金 2,000 元\\n排名第 21 - 50 名的参赛者可获顺丰科技提供的「顺丰文化包 + 顺丰笔记本 + 顺丰口罩」x 1\"}]', 0, 0, 0, 1, '信息与通信工程学院', 0, 2, '「顺丰科技 & 力扣」', '信息与通信工程学院', 16, '李老师 15016316253', '2023-03-24 00:00:00', '2023-03-31 00:00:00', 8, '2023-03-10 23:56:13');
INSERT INTO `competition` VALUES (62, '招商银行专场竞赛', 'http://localhost:8088/resources/competition_62.jpg', 0, 1, 0, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 1, 2, 8, 1, '信息与通信工程学院', 0, 2, '招商银行专场竞赛', '信息与通信工程学院', 16, '黄老师 15013262152', '2023-03-24 00:00:00', '2023-03-31 00:00:00', 2, '2023-03-10 23:57:52');
INSERT INTO `competition` VALUES (63, '禾赛科技专场竞赛', 'http://localhost:8088/resources/competition_63.jpg', 0, 1, 1, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 1, 1, 8, 0, '信息与通信工程学院', 0, 2, '「禾赛科技 & 力扣」', '信息与通信工程学院', 16, '李老师 16203142523', '2023-03-24 00:00:00', '2023-03-31 00:00:00', 3, '2023-03-11 00:00:01');
INSERT INTO `competition` VALUES (64, '「微爱思扣 以 Code 会友」专场竞赛', 'http://localhost:8088/resources/competition_64.jpg', 0, 1, 0, 0, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"进行中\",\"competitionStageBeginTime\":\"2023-03-12 00:00:00\",\"competitionStageEndTime\":\"2023-03-12 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"结束\",\"competitionStageBeginTime\":\"2023-03-13 00:00:00\",\"competitionStageEndTime\":\"2023-03-13 00:00:00\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, '', 0, 4, '微软&力扣', '信息与通信工程学院', 21, '李老师 13201235214', '2023-03-13 00:00:00', '2023-03-13 00:00:00', 2, '2023-03-14 11:52:37');
INSERT INTO `competition` VALUES (65, '2022全国院校室内设计技能大赛总决赛', 'http://localhost:8088/resources/competition_65.jpg', 0, 1, 0, 1, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, '', 0, 2, '中国室内装饰协会', '电子科技学院', 21, '', '2023-03-22 00:00:00', '2023-03-23 00:00:00', 2, '2023-03-14 12:07:48');
INSERT INTO `competition` VALUES (66, '第六届“光威杯”中国复合材料学会 大学生科技创新竞赛', 'http://localhost:8088/resources/competition_66.jpg', 0, 1, 0, 1, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行\",\"competitionStageIntro\":\"进行\",\"competitionStageBeginTime\":\"2024-06-06 00:00:00\",\"competitionStageEndTime\":\"2024-06-22 00:00:00\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 0, 0, 0, 0, '', 0, 2, '中国复合材料学会', '轮机学院', 21, '', '2023-03-24 00:00:00', '2023-03-31 00:00:00', 6, '2023-03-14 12:21:10');
INSERT INTO `competition` VALUES (67, '未来佳期专场竞赛', 'http://localhost:8088/resources/competition_67.jpg', 0, 1, 0, 1, '[{\"competitionStageName\":\"预报名\",\"competitionStageIntro\":\"平台预报名\",\"competitionStageBeginTime\":\"2023-01-01 00:00:00\",\"competitionStageEndTime\":\"2023-01-03 00:00:00\"},{\"competitionStageName\":\"初筛\",\"competitionStageIntro\":\"校内选拔赛\",\"competitionStageBeginTime\":\"2023-02-04 00:00:00\",\"competitionStageEndTime\":\"2023-02-20 00:00:00\"},{\"competitionStageName\":\"报名\",\"competitionStageIntro\":\"确定报名者名单\",\"competitionStageBeginTime\":\"2023-03-07 00:00:00\",\"competitionStageEndTime\":\"2023-03-09 00:00:00\"},{\"competitionStageName\":\"进行中\",\"competitionStageIntro\":\"\",\"competitionStageBeginTime\":\"2023-04-10 00:00:00\",\"competitionStageEndTime\":\"2023-06-10 00:00:00\"},{\"competitionStageName\":\"结束\",\"competitionStageIntro\":\"等待成绩\",\"competitionStageBeginTime\":\"2023-06-11 00:00:00\",\"competitionStageEndTime\":\"\"}]', '[{\"infoTitle\":\"提示标题：大赛目标\",\"infoContent\":\"提示内容：践行社会主义核心价值观，大力弘扬科学精神，培育理性思维，让科技创新与科学普及两翼齐飞，彰显科学素质建设在经济社会发展中的基础作用；以优质科普教育资源，助力“双减”政策有效落地，推动绿色发展，促进关注人与自然和谐共生，走可持续发展之路。\"}]', 1, 1, 8, 1, '信息与通信工程学院', 0, 2, '佳期', '信息与通信工程学院', 21, '', '2023-03-24 00:00:00', '2023-03-24 00:00:00', 4, '2023-03-14 12:30:30');

-- ----------------------------
-- Table structure for competition_file
-- ----------------------------
DROP TABLE IF EXISTS `competition_file`;
CREATE TABLE `competition_file`  (
  `competition_file_id` int NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `competition_id` int NOT NULL COMMENT '竞赛id',
  `competition_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `competition_file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `competition_file_announce_time` timestamp NULL DEFAULT NULL COMMENT '时间',
  `competition_file_type` tinyint NULL DEFAULT NULL COMMENT '文件类型，0竞赛附件，1竞赛成绩，2报名竞赛上传资料',
  PRIMARY KEY (`competition_file_id`, `competition_id`) USING BTREE,
  INDEX `fk_competition_id_3`(`competition_id` ASC) USING BTREE,
  CONSTRAINT `fk_competition_id_3` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`competition_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of competition_file
-- ----------------------------
INSERT INTO `competition_file` VALUES (46, 56, '天堂硅谷·数字经济算法编程大赛.docx', 'http://localhost:8088/resources/56_0天堂硅谷·数字经济算法编程大赛.docx', '2023-03-09 17:29:17', 0);
INSERT INTO `competition_file` VALUES (50, 60, '2022年度杭州未来科技城数字经济人才编程大赛.docx', 'http://localhost:8088/resources/60_02022年度杭州未来科技城数字经济人才编程大赛.docx', '2023-03-14 22:23:22', 0);
INSERT INTO `competition_file` VALUES (51, 60, '大赛规则.docx', 'http://localhost:8088/resources/60_0大赛规则.docx', '2023-03-14 22:24:01', 0);
INSERT INTO `competition_file` VALUES (52, 60, '大赛成绩.xlsx', 'http://localhost:8088/resources/60_1大赛成绩.xlsx', '2023-03-14 22:25:14', 1);
INSERT INTO `competition_file` VALUES (53, 60, '王梅-报名表.docx', 'http://localhost:8088/resources/60_2王梅-报名表.docx', '2023-03-14 22:28:20', 2);
INSERT INTO `competition_file` VALUES (54, 60, '吕小布-报名表.docx', 'http://localhost:8088/resources/60_2吕小布-报名表.docx', '2023-03-14 22:28:20', 2);
INSERT INTO `competition_file` VALUES (55, 60, '张芷箬-报名表.docx', 'http://localhost:8088/resources/60_2张芷箬-报名表.docx', '2023-03-14 22:28:20', 2);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `team_id` int NULL DEFAULT NULL COMMENT '队伍id',
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `message_time` timestamp NULL DEFAULT NULL COMMENT '时间',
  `message_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转页面url',
  `message_type` tinyint NULL DEFAULT NULL COMMENT '类型 0通知 1申请 2评论 3推送 4系统公告',
  `message_object_type` tinyint NULL DEFAULT NULL COMMENT '对象类型 0个人消息 1队伍消息 2新闻 3公告',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `fk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 16, NULL, '用户 MZY123评论了你的帖子: 教程：使用Jmeter对带token的接口进行压测', '2023-03-13 21:37:46', 'article_details?articleId=5', 2, 0);
INSERT INTO `message` VALUES (2, 16, NULL, '用户 MZY123回复了你的评论。帖子(教程：使用Jmeter对带token的接口进行压测)', '2023-03-13 21:38:24', 'article_details?articleId=5', 2, 0);
INSERT INTO `message` VALUES (3, NULL, 13, '提交了待处理的申请: 禾赛科技专场竞赛', '2023-03-13 22:05:46', 'backstage?focusIndex=4', 0, 1);
INSERT INTO `message` VALUES (4, 16, NULL, '鸡你太美 申请参加禾赛科技专场竞赛', '2023-03-13 22:05:46', 'backstage?focusIndex=4', 1, 0);
INSERT INTO `message` VALUES (5, NULL, 13, '申请通过: 禾赛科技专场竞赛', '2023-03-13 22:18:55', 'competition_details?competitionId=63', 0, 1);
INSERT INTO `message` VALUES (6, 19, NULL, '账号被封禁，请联系管理员', '2023-03-13 23:35:04', '', 0, 0);
INSERT INTO `message` VALUES (7, 20, NULL, '账号被封禁，请联系管理员', '2023-03-13 23:35:32', '', 0, 0);
INSERT INTO `message` VALUES (8, 16, NULL, '提交了队伍申请：美特斯邦威', '2023-03-14 13:20:54', 'team_details?teamId=9', 0, 0);
INSERT INTO `message` VALUES (9, NULL, 9, 'MZY123申请加入：美特斯邦威', '2023-03-14 13:20:54', 'backstage?focusIndex=7', 0, 1);
INSERT INTO `message` VALUES (10, 16, NULL, '提交了队伍申请：鸡你太美', '2023-03-14 13:21:32', 'team_details?teamId=13', 0, 0);
INSERT INTO `message` VALUES (11, NULL, 13, 'MZY123申请加入：鸡你太美', '2023-03-14 13:21:32', 'backstage?focusIndex=7', 0, 1);
INSERT INTO `message` VALUES (12, 16, NULL, '提交了待处理的申请: 2022全国院校室内设计技能大赛总决赛', '2023-03-14 22:09:42', 'competition_details?competitionId=65', 0, 0);
INSERT INTO `message` VALUES (13, 21, NULL, '黄冠滨 申请参加2022全国院校室内设计技能大赛总决赛', '2023-03-14 22:09:42', 'backstage?focusIndex=4', 1, 0);
INSERT INTO `message` VALUES (14, 16, NULL, '提交了待处理的申请: 第六届“光威杯”中国复合材料学会 大学生科技创新竞赛', '2023-03-14 22:09:50', 'competition_details?competitionId=66', 0, 0);
INSERT INTO `message` VALUES (15, 21, NULL, '黄冠滨 申请参加第六届“光威杯”中国复合材料学会 大学生科技创新竞赛', '2023-03-14 22:09:50', 'backstage?focusIndex=4', 1, 0);
INSERT INTO `message` VALUES (16, NULL, 2, '提交了待处理的申请: 未来佳期专场竞赛', '2023-03-14 22:14:29', 'competition_details?competitionId=67', 0, 1);
INSERT INTO `message` VALUES (17, 21, NULL, '精灵小组 申请参加未来佳期专场竞赛', '2023-03-14 22:14:29', 'backstage?focusIndex=4', 1, 0);
INSERT INTO `message` VALUES (18, 16, NULL, '参赛资格被取消：2022年度杭州未来科技城数字经济人才编程大赛', '2023-03-14 22:29:00', 'backstage?focusIndex=2', 0, 0);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `news_id` int NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `news_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目',
  `news_digest` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '摘要',
  `news_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `news_publish_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (44, '陈云贤副省长在广州航海学院现场办公', '2013年8月22日下午，陈云贤副省长带领省教育厅、省财政厅、省编办、省府研究院等部门负责人来到广州航海学院召开现场办公会', '<p>2013年8月22日下午，陈云贤副省长带领省教育厅、省财政厅、省编办、省府研究院等部门负责人来到广州航海学院召开现场办公会，协调解决广州医科大学、广东财经大学、广州航海学院等广东三所新更名和升本院校在建设发展过程中面临的困难和突出问题，会议由省政府江海燕副秘书长主持。</p>\n<p>陈云贤副省长一行在广州航海学院党委王舒平书记等校领导的陪同下实地考察了广州航海学院北区建设工地，详细了解北区建设项目和工程进度；参观考察了校史馆和学校大型海船操纵模拟实训室，了解学校的办学历史、办学特色和实验室建设情况。</p>\n<p>参观考察结束后，陈云贤副省长一行在广州航海学院行政楼三楼会议室召开座谈会，与广州医科大学、广东财经大学、广州航海学院的党政领导进行亲切座谈，分别听取三所院校主要负责人关于学校建设发展情况和更名为大学、升格为本科院校后的发展思路、办学定位、办学理念、人才培养及发展建设中遇到的实际困难、突出问题等情况的汇报。省教育厅、省财政厅、省编办和省委组织部相关负责人对三所高校的建设情况给予了积极回应，都表示将一如既往、全力以赴地支持三所院校的发展建设。</p>\n<p>陈云贤副省长在听取三所院校的情况汇报和省政府相关部门的回应后发表重要讲话。陈云贤副省长对三所学校成功更名为大学和升格为本科院校表示热烈祝贺，并充分肯定近年来三所院校在学科建设、社会服务、人才培养模式改革上的突出成绩。陈云贤副省长要求省直各部门要按照相关规定，在资金、教学、人才引进等方面形成合力，加大支持力度，全力支持三所更名和升格高校的发展建设，确保三所院校在更名和升本后各项建设项目和配套措施能够得到落实，共同推进学校的快速发展。陈云贤副省长也希望三所院校要鼓足干劲、树立信心、正视困难，以更名和升格为契机，以特色谋发展，以发展促创新，乘势而上，取得成效，实现内涵跨越式发展，为教育事业和社会发展作出更大贡献。<br />\n<img src=\"http://localhost:8088/resources/16787691443819e9.jpg\" alt=\"9C29B9050A371A74CE38F51FDFE_66B472DF_1AFF5.jpg\" /></p>\n', '2023-03-14 12:45:58');
INSERT INTO `news` VALUES (45, '朋辈引领，青春同行——外语学院举办“朋辈思政”之“心理引导员”培训活动', '（外语学院 供稿）“朋辈思政”突破传统思政的单一主体性，作为一种新的思政理念与模式在高校思想政治教育各领域发挥着重要的作用。', '<p>（外语学院 供稿）“朋辈思政”突破传统思政的单一主体性，作为一种新的思政理念与模式在高校思想政治教育各领域发挥着重要的作用。为协同“朋辈力量”在学院心理健康教育工作中的作用，外语学院于3月9日在文理楼508会议室举办了“心理引导员”培训，开展学院心理健康教育系列活动，活动由学院分团委书记冯诗琦组织，分团委学生干部及各班心理委员参加活动。<br />\n<img src=\"http://localhost:8088/resources/167876923205749f.png\" alt=\"88270FFAE60ECC4DD39DFF21C94_341784FA_8F9D9.png\" /><br />\n冯老师首先深入浅出地阐明了“朋辈思政”的含义，点明朋辈交流的优势，引导心理委员明确“心理引导员”的身份和作用。春季是心理问题的高发期，新学期的到来伴随着疫情的发展和变化，同学们的生理心理都呈现出新的特点。开学后的考试安排及其他各项任务接连不断，考验着同学们的心理承压能力和任务分解能力，“心理引导员”要做好“知心”和“共情”，做到善于观察、积极发现；开展活动，合理疏导；及时上报、有效干预，做好同学们的心理引导和排解工作。</p>\n<p>本次活动对完善学院心理健康工作“班-院-校-家庭”四级防御体系，有效预防学生心理问题发生具有非常重要的作用。</p>\n', '2023-03-14 12:47:23');
INSERT INTO `news` VALUES (46, '厚植爱国情怀，砥砺青春梦想——海运学院开展新学期升旗仪式暨主题教育活动', '春风送暖，共赴新程。为加强学生爱国主义教育，深化半军事管理内涵建设，3月6日上午，海运学院在海燕广场开展新学期升旗仪式暨主题教育活动。', '<p>（海运学院 供稿）春风送暖，共赴新程。为加强学生爱国主义教育，深化半军事管理内涵建设，3月6日上午，海运学院在海燕广场开展新学期升旗仪式暨主题教育活动。海运学院大队长林少栋,中队长何淋、叶振斌、陈岸斌、林恬恬等老师和全体学生参加本次活动。<br />\n<img src=\"http://localhost:8088/resources/16787693223940ff.jpg\" alt=\"9285B3E6380608F35D4700C4738_4391B3CC_221CC.jpg\" /></p>\n<p>清晨6点50分，全体师生已经在海燕广场就位，同学们身穿校服，队列整齐，精神饱满。7点整，升旗仪式正式开始。国旗护卫队迈着整齐划一、铿锵有力的步伐，护送五星红旗正步走向旗杆。全体师生肃立，向国旗行注目礼。伴随着庄严的《义勇军进行曲》，五星红旗冉冉升起，在熠熠晨光中迎风飘扬。</p>\n<p><img src=\"http://localhost:8088/resources/16787693362714c7.jpg\" alt=\"06163CBA6E65CB13963776FC494_C17BFBAA_17D87.jpg\" /></p>\n<p>随后，在主题教育活动中，林少栋大队长首先对同学们良好的精神风貌表示肯定。他指出，2022年海运学子表现良好，严格遵守各项规章制度，自觉做好疫情防控“第一责任人”，大证统考及法考保持较高的通过率。他强调，作为学校半军事管理的重要组成部分，每周一的升旗仪式是同学们爱国情、强国志、报国行的精神凝聚与情感寄托，希望同学们在接下来的学习生活中，以半军管培养出来的坚毅和担当，展现海运学子砥砺向上的精神力量。最后也对同学们提出了期望，一要胸怀祖国，让爱国主义精神在心中牢牢扎根；二要不断深化半军事管理内涵，提高体育锻炼意识和自主学习意识，在自主学习中实现学有所思、学有所获和学有所成，做到“天天有进步，周周有收获”；三要肩负历史使命，坚定前进信心，立大志、明大德、成大才、担大任，在激扬青春、开拓人生、奉献社会的进程中展现青春风采。</p>\n', '2023-03-14 12:49:08');
INSERT INTO `news` VALUES (47, '喜讯！我校占毅教授主笔的党建研究成果获市直机关二等奖', '近日，我校马克思主义学院教授、南风窗传媒智库研究员占毅教授接到南风窗传媒智库发来的感谢函：', '<p>近日，我校马克思主义学院教授、南风窗传媒智库研究员占毅教授接到南风窗传媒智库发来的感谢函：</p>\n<p>兹感谢贵校（广州航海学院）占毅教授撰写的课题文章《加强新时代青年党员干部对党忠诚教育的思考》投稿到南风窗杂志社、南风窗传媒智库，提交广州市委宣传部被采纳，该课题代表广州市委宣传部荣获“2022年度广州市直属机关党建课题研究二等奖”，特此感谢！</p>\n<p>近日，中共广州市直属机关工作委员会公布了2022年度广州市直属机关优秀党建课题研究成果通报。文件指出，2022年，市直机关工委全面贯彻落实党的二十大精神，聚焦新时代机关党建的使命任务和市委中心工作，组织实施了“习近平总书记关于党的自我革命战略思想研究”“加强对党忠诚教育研究”“全面激发机关基层党组织生机活力研究”等重点课题研究，受到市直各机关党组织高度重视与积极参与，形成了一批理论与实践相结合的高质量研究成果，对与时俱进做好机关党建工作具有重要的参考价值。经专家认真评审、市直机关工委审定，共评选出优秀成果61篇，其中一等奖10篇、二等奖20篇、三等奖31篇，并给予6个单位组织奖。</p>\n<p>近日，中共广州市直属机关工作委员会公布了2022年度广州市直属机关优秀党建课题研究成果通报。文件指出，2022年，市直机关工委全面贯彻落实党的二十大精神，聚焦新时代机关党建的使命任务和市委中心工作，组织实施了“习近平总书记关于党的自我革命战略思想研究”“加强对党忠诚教育研究”“全面激发机关基层党组织生机活力研究”等重点课题研究，受到市直各机关党组织高度重视与积极参与，形成了一批理论与实践相结合的高质量研究成果，对与时俱进做好机关党建工作具有重要的参考价值。经专家认真评审、市直机关工委审定，共评选出优秀成果61篇，其中一等奖10篇、二等奖20篇、三等奖31篇，并给予6个单位组织奖。</p>\n', '2023-03-14 12:53:17');
INSERT INTO `news` VALUES (48, '【大学】广州航海学院简介', '广州航海学院创办于1964年，是广州市人民政府管理的公办普通本科院校，是华南地区唯一一所独立建制的海事本科院校，广东省“文明校园”单位。', '<p>广州航海学院创办于1964年，是广州市人民政府管理的公办普通本科院校，是华南地区唯一一所独立建制的海事本科院校，广东省“文明校园”单位，广东省“冲补强”计划“强特色”建设高校，广东省硕士学位授予立项建设单位，广东省基层人民武装部建设省级试点单位，连续六年荣获广东省“征兵工作先进单位”；是国际海事组织认可的航海院校，我国华南地区航运业高级人才重要的培养基地，被誉为“航海家的摇篮”。</p>\n<p><img src=\"http://localhost:8088/resources/1678770450064145.jpg\" alt=\"81912A72B4AEA65128E873FC2F2_5EF6CF86_16E1B.jpg\" /></p>\n<p>（黄埔校区校门和南教学楼）</p>\n<p><img src=\"http://localhost:8088/resources/1678770468532467.jpg\" alt=\"3944941B7BF8E9AB0937B10359F_D790346B_1EF95.jpg\" /></p>\n<p>（黄埔校区北区教学楼）</p>\n<p><img src=\"http://localhost:8088/resources/1678770489665fb5.jpg\" alt=\"B6E93F5F90B03176E1B8572A682_9E75EC13_25499.jpg\" /></p>\n<p>（“航海家的摇篮”荣誉石，由我校校友、书法家陈春盛题字）</p>\n<p>2018年，广东省委、广州市委明确以广州航海学院为基础筹建广州交通大学。2020年12月教育部、广东省人民政府联合发布《推进粤港澳大湾区高等教育合作发展规划》，明确“推动建设广州交通大学等高校”、“支持广州建设广州交通大学”，将广州建设广州交通大学列入重点项目清单。2021年5月《广州市国民经济和社会发展第十四个五年规划和2035年远景目标纲要》提出：加快建设广州交通大学，打造高水平有特色的应用型大学。2021年10月，广东省人民政府印发《广东省教育发展“十四五”规划》，要求加快推动高起点、高标准建设广州交通大学等高校。</p>\n<p><img src=\"http://localhost:8088/resources/1678770517397248.jpg\" alt=\"EC31C28453262FBB09D43F6803E_AAE7CCE9_279B8.jpg\" /></p>\n<p>（学校南门）</p>\n<p>学院校园面积约525亩，现有两个校区，其中黄埔校区毗邻闻名世界的黄埔军校，琶洲校区坐落于“古代海上丝绸之路”的溯源地广州黄埔古港。2021年广州市规划和自然资源局明确，广州交通大学在广州航海学院黄埔校区周边征地413亩，总用地面积为978亩。</p>\n', '2023-03-14 13:08:58');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` int NULL DEFAULT 0 COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '操作竞赛', NULL, NULL, '0', '0', 'system:competition', '#', NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2, '操作活动', NULL, NULL, '0', '0', 'system:activity', '#', NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (3, '操作队伍', NULL, NULL, '0', '0', 'system:team', '#', NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (4, '操作帖子', NULL, NULL, '0', '0', 'system:article', '#', NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (5, '管理所有', NULL, NULL, '0', '0', 'system:all', '#', NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (6, '操作新闻公告', NULL, NULL, '0', '0', 'system:n_a', '#', NULL, NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` int NULL DEFAULT 0 COMMENT 'del_flag',
  `create_by` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '学生', 'student', '0', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (2, '教师', 'teacher', '0', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (3, '管理员', 'admin', '0', 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` int NOT NULL DEFAULT 0 COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 6);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` int NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (16, 3);
INSERT INTO `sys_user_role` VALUES (17, 1);
INSERT INTO `sys_user_role` VALUES (18, 1);
INSERT INTO `sys_user_role` VALUES (19, 1);
INSERT INTO `sys_user_role` VALUES (20, 1);
INSERT INTO `sys_user_role` VALUES (21, 2);
INSERT INTO `sys_user_role` VALUES (22, 2);
INSERT INTO `sys_user_role` VALUES (23, 1);
INSERT INTO `sys_user_role` VALUES (24, 1);
INSERT INTO `sys_user_role` VALUES (25, 1);
INSERT INTO `sys_user_role` VALUES (26, 1);
INSERT INTO `sys_user_role` VALUES (27, 1);
INSERT INTO `sys_user_role` VALUES (28, 1);
INSERT INTO `sys_user_role` VALUES (29, 1);
INSERT INTO `sys_user_role` VALUES (30, 1);
INSERT INTO `sys_user_role` VALUES (31, 1);
INSERT INTO `sys_user_role` VALUES (32, 1);
INSERT INTO `sys_user_role` VALUES (33, 1);
INSERT INTO `sys_user_role` VALUES (34, 1);
INSERT INTO `sys_user_role` VALUES (35, 1);
INSERT INTO `sys_user_role` VALUES (36, 1);
INSERT INTO `sys_user_role` VALUES (37, 1);
INSERT INTO `sys_user_role` VALUES (38, 1);

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `team_id` int NOT NULL AUTO_INCREMENT COMMENT '队伍id',
  `team_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队伍logo',
  `team_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队伍名称',
  `team_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队伍宣言',
  `team_create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `team_caption_id` int NULL DEFAULT NULL COMMENT '队长id',
  `team_caption_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队长真实姓名',
  `team_caption_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队长昵称',
  `team_member_num_max` int NULL DEFAULT NULL COMMENT '队伍人数上限',
  `team_member_num` int NULL DEFAULT 1 COMMENT '目前人数',
  `team_star` double NULL DEFAULT 0 COMMENT '星级（评分）',
  `team_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签（数组）',
  `team_status` tinyint NULL DEFAULT NULL COMMENT '状态，0不招新，1招新',
  `team_college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院（可为空，即跨学院）',
  `team_specialty` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业（可为空，即跨专业）',
  `team_invite_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请码',
  PRIMARY KEY (`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES (1, 'http://localhost:8088/resources/logo_4.png', '不挂机队', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-01-01 15:14:40', 16, '黄冠滨', 'MZY123', 12, 6, 4.24, '[\"Java\",\"C++\",\"Python\",\"ABC\"]', 1, '信息与通信工程学院', '软件工程', '23d1b1');
INSERT INTO `team` VALUES (2, 'http://localhost:8088/resources/logo_9.png', '精灵小组', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 16, '黄冠滨', 'MZY123', 12, 1, 0.11, '[\"Java\",\"C++\",\"Python\",\"ABC\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (3, 'http://localhost:8088/resources/logo_3.png', '梦想组', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 16, '黄冠滨', 'MZY123', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (4, 'http://localhost:8088/resources/logo_4.png', '欢乐城堡组', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 16, '黄冠滨', 'MZY123', 12, 1, 0.11, '[\"Java\",\"C++\",\"Python\",\"ABC\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (5, 'http://localhost:8088/resources/logo_5.png', '奔驰财团', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 16, '黄冠滨', 'MZY123', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (6, 'http://localhost:8088/resources/logo_6.png', '跃起动力', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 17, '张三丰', '谢谢你Giao', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', '123456');
INSERT INTO `team` VALUES (7, 'http://localhost:8088/resources/logo_7.png', '超越组', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 17, '张三丰', '谢谢你Giao', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (8, 'http://localhost:8088/resources/logo_8.png', '天使小组', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 17, '张三丰', '谢谢你Giao', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (9, 'http://localhost:8088/resources/logo_9.png', '美特斯邦威', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 17, '张三丰', '谢谢你Giao', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (10, 'http://localhost:8088/resources/logo_10.png', '奇思妙想', '越战越勇.越挫越强.不放弃,不抛弃！', '2023-03-09 13:37:59', 17, '张三丰', '谢谢你Giao', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (11, 'http://localhost:8088/resources/logo_11.png', '百事队', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 19, '苏星婕', '兔兔真可爱', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 0, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (12, 'http://localhost:8088/resources/logo_12.png', '野狼团', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 19, '苏星婕', '兔兔真可爱', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (13, 'http://localhost:8088/resources/logo_13.png', '鸡你太美', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 19, '苏星婕', '兔兔真可爱', 12, 1, 0.77, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (14, 'http://localhost:8088/resources/logo_14.png', '奇迹', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 19, '苏星婕', '兔兔真可爱', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (15, 'http://localhost:8088/resources/logo_15.png', '火狼', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 19, '苏星婕', '兔兔真可爱', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (16, 'http://localhost:8088/resources/logo_16.png', '智慧小组', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 25, '鸡太美', '小鸡1号', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (17, 'http://localhost:8088/resources/logo_17.png', '花儿朵朵', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 25, '鸡太美', '小鸡1号', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (18, 'http://localhost:8088/resources/logo_18.png', '阳光小组', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 25, '鸡太美', '小鸡1号', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (19, 'http://localhost:8088/resources/logo_4.png', '先锋队', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 25, '鸡太美', '小鸡1号', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (20, 'http://localhost:8088/resources/logo_5.png', '实力队', '召之即来、来之能战、战则必胜、永不言败', '2023-03-09 13:37:59', 25, '鸡太美', '小鸡1号', 12, 1, 0.11, '[\"标签1\",\"标签2\"]', 1, '信息与通信工程学院', '软件工程', 'null');
INSERT INTO `team` VALUES (21, 'http://localhost:8088/resources/logo_1.png', '开心一家人', '开心一家人', '2023-03-09 21:28:32', 24, '海鲜鱼', '我是咸鱼1号', 6, 1, 0.1, '[\"舞蹈\",\"五大领域\"]', 1, '艺术学院', '学前教育', '76d1b1');

-- ----------------------------
-- Table structure for team_activity
-- ----------------------------
DROP TABLE IF EXISTS `team_activity`;
CREATE TABLE `team_activity`  (
  `team_id` int NOT NULL,
  `activity_id` int NOT NULL,
  `status` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`team_id`, `activity_id`) USING BTREE,
  INDEX `fk_activity_id_2`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `fk_activity_id_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_team_id_3` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team_activity
-- ----------------------------
INSERT INTO `team_activity` VALUES (1, 32, 1);

-- ----------------------------
-- Table structure for team_competition
-- ----------------------------
DROP TABLE IF EXISTS `team_competition`;
CREATE TABLE `team_competition`  (
  `team_id` int NOT NULL,
  `competition_id` int NOT NULL,
  `status` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`team_id`, `competition_id`) USING BTREE,
  INDEX `fk_competition_id_2`(`competition_id` ASC) USING BTREE,
  CONSTRAINT `fk_competition_id_2` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`competition_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_team_id_2` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team_competition
-- ----------------------------
INSERT INTO `team_competition` VALUES (1, 57, 1);
INSERT INTO `team_competition` VALUES (2, 67, 1);
INSERT INTO `team_competition` VALUES (3, 59, 1);
INSERT INTO `team_competition` VALUES (4, 58, 1);
INSERT INTO `team_competition` VALUES (13, 63, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `user_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别，0女，1男',
  `intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `identity` tinyint NULL DEFAULT NULL COMMENT '身份，0学生，1教师，2管理员',
  `num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号或工号（例如，学号：201915270205，工号：201901）',
  `college` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院',
  `specialty` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `clazz` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `reg_date` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '账号状态 0注销 1正常 2封禁',
  PRIMARY KEY (`id`, `phone`) USING BTREE,
  INDEX `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (16, '13168162412', 'MZY123', 'http://localhost:8088/resources/16.jpg', 1, '那年我双手插兜，不知道什么是对手', '$2a$10$CGQgy0ogfYjim2f2qgMaHuNPnbLkNBJJVxTNQNTTDEqAHbHjBDSTi', '黄冠滨', 2, '201915270205', '信息与通信工程学院', '软件工程', '192', '12sfdsdfsdf3@162.com', '2023-02-12 02:01:04', 1);
INSERT INTO `user` VALUES (17, '15013644261', '高启盛他哥', 'http://localhost:8088/resources/17.jpg', 1, '此用户很懒什么也没留下', '$2a$10$VBi4amlBbwAu87EvFlG1Meaw2Kkfxs9UmjUNQQez31r26zP2qwcmm', '张三丰', 0, '201915240307', '信息与通信工程学院', '软件工程', '192', '15013644261@163.com', '2023-02-18 15:37:47', 1);
INSERT INTO `user` VALUES (18, '15362411254', '米老师', 'http://localhost:8088/resources/18.jpg', 0, '此用户很懒什么也没留下', '$2a$10$yuHfMps/7zdB0dMO/CBiLuIiaVpSc9rLlrgP/T55t3bGuoVY31teS', '张芷箬', 0, '201925130214', '信息与通信工程学院', '软件工程', '192', '15362411254@163.com', '2023-02-18 15:47:05', 1);
INSERT INTO `user` VALUES (19, '13423653321', '兔兔真可爱', 'http://localhost:8088/resources/19.jpg', 1, '此用户很懒什么也没留下', '$2a$10$IpypWQglO3Q7T30HkBCleOqToaUINbIxsxubvS9ffT812latGE31q', '苏星婕', 0, '201936250120', '信息与通信工程学院', '软件工程', '192', '13423653321@163.com', '2023-02-18 15:49:21', 2);
INSERT INTO `user` VALUES (20, '18907998754', '卉卉12138', 'http://localhost:8088/resources/20.jpg', 1, '此用户很懒什么也没留下', '$2a$10$cJiXPA2C6GuVGYmp2eo3b.gFB5CxlJEPfRl9uBNyvYm8eJlKP8QEm', '刘大卉', 0, '201925321025', '信息与通信工程学院', '软件工程', '192', '18907998754@163.com', '2023-02-18 15:53:44', 2);
INSERT INTO `user` VALUES (21, '13201235214', '我是李老师', 'http://localhost:8088/resources/21.jpg', 1, '此用户很懒什么也没留下', '$2a$10$VyUirXtAmriKNbbw0MiU4O1I0JkmAkaYoPjgWtlf4TXfVhbgDvfJO', '李老师', 1, '201901', '信息与通信工程学院', '软件工程', '192', '13201235214@163.com', '2023-02-18 15:56:36', 1);
INSERT INTO `user` VALUES (22, '13024001253', '我是青老师', 'http://localhost:8088/resources/22.jpg', 1, '此用户很懒什么也没留下', '$2a$10$UrJraMnq9YDmRNVk0TGJkOKKJXvvt.vRLG4KAVB10Z/manYX2c4pi', '青老师', 1, '201902', '信息与通信工程学院', '软件工程', '192', '13024001253@163.com', '2023-02-18 15:59:05', 1);
INSERT INTO `user` VALUES (23, '15026332415', 'missYoBa', 'http://localhost:8088/resources/23.jpg', 1, '此用户很懒什么也没留下', '$2a$10$FeD1cr.YHuLtP936.1JSGO5NoqJdkJT1CC/wwmDexjqql.NJ6F9p.', '王大同', 0, '201925201242', '信息与通信工程学院', '软件工程', '192', '16230021254@163.com', '2023-02-18 20:39:58', 1);
INSERT INTO `user` VALUES (24, '18230211125', '我是咸鱼1号', 'http://localhost:8088/resources/24.jpg', 1, '此用户很懒什么也没留下', '$2a$10$0.0J6XbQNa0eBawT3foAt.tOx/IUoBaDhSaBwsvNrn6KhCsZxf34.', '海鲜鱼', 0, '201923520123', '信息与通信工程学院', '软件工程', '192', '18230211125@163.com', '2023-02-18 20:42:14', 1);
INSERT INTO `user` VALUES (25, '15321021255', '小鸡1号', 'http://localhost:8088/resources/25.jpg', 1, '此用户很懒什么也没留下', '$2a$10$BeRo9OOnCFk..m1axtBWa.3oWkFJftdKhNXVjNe6qHAwgM.8KNdKm', '鸡太美', 0, '201925321254', '轮机学院', '轮机工程', '192', '15321021255@163.com', '2023-02-18 20:43:22', 1);
INSERT INTO `user` VALUES (27, '13000000000', '拉比小新', 'http://localhost:8088/resources/27.jpg', 1, '此用户很懒什么也没留下', '$2a$10$HQp8ziP/gvjlq9fHfh.aIujpTNye7wiJRuSKnW9r/.3O59RrkdXsu', '蜡笔小新', 0, '201915270200', '艺术学院', '信息传媒', '192', '13000000000@163.com', '2023-03-14 11:08:02', 1);
INSERT INTO `user` VALUES (28, '13000000001', '凉薄少女梦亡海', 'http://localhost:8088/resources/28.jpg', 0, '此用户很懒什么也没留下', '$2a$10$EKoH2gVFZEV9WDkIXeC91OMT9lJqdpC.1pHPpclpZ2mtVTw1AEhZC', '王梅', 0, '201915270201', '艺术学院', '信息传媒', '192', '13000000001@163.com', '2023-03-14 11:11:32', 1);
INSERT INTO `user` VALUES (29, '13000000002', '碎花小裙子', 'http://localhost:8088/resources/29.jpg', 0, '此用户很懒什么也没留下', '$2a$10$750O9mIRhALepanSIpa2fuiMCz1uTt5mxYCKC9tC0bgEhn/NU.PT6', '李海燕', 0, '201915270202', '艺术学院', '信息传媒', '192', '13000000002@163.com', '2023-03-14 11:13:08', 1);
INSERT INTO `user` VALUES (30, '13000000003', 'Lotus.忘忧', 'http://localhost:8088/resources/30.jpg', 1, '此用户很懒什么也没留下', '$2a$10$StdQq9efF2oe.1VDvQxhAO1mvRprged.ktx6/BAxZK9OGy.zpERT.', '陈璇童', 0, '201915270203', '艺术学院', '信息传媒', '192', '13000000003@163.com', '2023-03-14 11:16:15', 1);
INSERT INTO `user` VALUES (31, '13000000004', '风流吥丅流', 'http://localhost:8088/resources/31.jpg', 1, '此用户很懒什么也没留下', '$2a$10$MrBvPWBfFrELd/U/XbNYkezqenlkL5SezITfieANJ3/mrmh.jwLEW', '贱人曾', 0, '201915270206', '外语学院', '英语', '192', '13000000004@163.com', '2023-03-14 11:18:45', 1);
INSERT INTO `user` VALUES (32, '13000000005', '不是可达鸭', 'http://localhost:8088/resources/32.jpg', 1, '此用户很懒什么也没留下', '$2a$10$Ph4kxsXrZSu8pMtCZFFpveF9wC42dqOeYprprkzNy.8uteGMx04Iy', '杰尼龟', 0, '201915270207', '海事学院', '海事工程', '192', '13000000005@163.com', '2023-03-14 11:20:08', 1);
INSERT INTO `user` VALUES (33, '13000000006', '不是杰尼龟', 'http://localhost:8088/resources/33.jpg', 1, '此用户很懒什么也没留下', '$2a$10$cI1uY.HxXKU1NubQw7zotO6j/Om3PHFrYr8eij0DTF6/zMgVtBT8i', '可达鸭', 0, '201915270208', '海事学院', '海事工程', '192', '13000000006@163.com', '2023-03-14 11:22:28', 1);
INSERT INTO `user` VALUES (34, '13000000007', '佟湘玉', 'http://localhost:8088/resources/34.jpg', 0, '此用户很懒什么也没留下', '$2a$10$C/oQMIZn/68o45pTVBjhMOQbzOvkuxnbd1aTLZUFMmAZRc/u4ucV2', '佟湘玉', 0, '201915270209', '邮轮游艇学院', '游艇工程', '192', '13000000007@163.com', '2023-03-14 11:24:45', 1);
INSERT INTO `user` VALUES (35, '13000000008', '奶丝兔咪逑', 'http://localhost:8088/resources/35.jpg', 0, '此用户很懒什么也没留下', '$2a$10$wQNYWSUi1m2C2PnfXsNNfe/rXEvCcg.C3.g/zs92Grfk.M.Rt5uzC', '诺兰', 0, '201915270211', '艺术学院', '信息传媒', '192', '13000000008@163.com', '2023-03-14 11:30:23', 1);
INSERT INTO `user` VALUES (36, '13000000010', '.N咖啡+免*', 'http://localhost:8088/resources/36.jpg', 0, '此用户很懒什么也没留下', '$2a$10$Imo9ZZqhCj7xddUHomQqf.DVGkTbWIclD8OI61y1lKhmvSxTD8qVy', '菲菲兔', 0, '201915270210', '信息与通信工程学院', '计算机科学', '192', '13000000010@163.com', '2023-03-14 11:32:43', 1);
INSERT INTO `user` VALUES (37, '13000000011', '帅的被人砍', 'http://localhost:8088/resources/37.jpg', 1, '此用户很懒什么也没留下', '$2a$10$qui7c364JkVLuR5CwoD0AepRTSe0No21Q51Jyg7tV883P5EhMjXlG', '吕小布', 0, '201915270220', '信息与通信工程学院', '计算机科学', '192', '13000000011@163.com', '2023-03-14 11:34:11', 1);
INSERT INTO `user` VALUES (38, '13000000012', '小姨妈', 'http://localhost:8088/resources/38.jpg', 0, '此用户很懒什么也没留下', '$2a$10$UwDZuJmjGoYq9Bz6PPqi5umr7tT0aBJzZp2qdId7pKhiL0L6xUcRu', '唐悠悠', 0, '201915270215', '信息与通信工程学院', '计算机科学', '192', '13000000012@163.com', '2023-03-14 11:35:27', 1);

-- ----------------------------
-- Table structure for user_activity
-- ----------------------------
DROP TABLE IF EXISTS `user_activity`;
CREATE TABLE `user_activity`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `activity_id` int NOT NULL COMMENT '活动id',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`user_id`, `activity_id`) USING BTREE,
  INDEX `fk_activity_id`(`activity_id` ASC) USING BTREE,
  CONSTRAINT `fk_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_activity
-- ----------------------------
INSERT INTO `user_activity` VALUES (16, 30, 1);
INSERT INTO `user_activity` VALUES (16, 33, 1);
INSERT INTO `user_activity` VALUES (20, 30, 1);
INSERT INTO `user_activity` VALUES (25, 31, 1);

-- ----------------------------
-- Table structure for user_competition
-- ----------------------------
DROP TABLE IF EXISTS `user_competition`;
CREATE TABLE `user_competition`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `competition_id` int NOT NULL COMMENT '竞赛id',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（0未确认，1确认）',
  PRIMARY KEY (`user_id`, `competition_id`) USING BTREE,
  INDEX `fk_competition_id`(`competition_id` ASC) USING BTREE,
  CONSTRAINT `fk_competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`competition_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_competition
-- ----------------------------
INSERT INTO `user_competition` VALUES (16, 61, 1);
INSERT INTO `user_competition` VALUES (16, 65, 1);
INSERT INTO `user_competition` VALUES (16, 66, 1);
INSERT INTO `user_competition` VALUES (18, 60, 1);
INSERT INTO `user_competition` VALUES (18, 61, 1);
INSERT INTO `user_competition` VALUES (19, 60, 1);
INSERT INTO `user_competition` VALUES (23, 56, 1);
INSERT INTO `user_competition` VALUES (27, 60, 1);
INSERT INTO `user_competition` VALUES (28, 60, 1);
INSERT INTO `user_competition` VALUES (33, 60, 1);
INSERT INTO `user_competition` VALUES (34, 60, 1);
INSERT INTO `user_competition` VALUES (37, 60, 1);

-- ----------------------------
-- Table structure for user_team
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `team_id` int NOT NULL COMMENT '队伍id',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态 0未通过 1通过',
  PRIMARY KEY (`user_id`, `team_id`) USING BTREE,
  INDEX `fk_team_id`(`team_id` ASC) USING BTREE,
  CONSTRAINT `fk_team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_team
-- ----------------------------
INSERT INTO `user_team` VALUES (16, 6, 1);
INSERT INTO `user_team` VALUES (16, 7, 1);
INSERT INTO `user_team` VALUES (16, 8, 1);
INSERT INTO `user_team` VALUES (16, 9, 0);
INSERT INTO `user_team` VALUES (16, 13, 1);
INSERT INTO `user_team` VALUES (18, 3, 1);
INSERT INTO `user_team` VALUES (18, 5, 1);
INSERT INTO `user_team` VALUES (18, 8, 0);
INSERT INTO `user_team` VALUES (18, 10, 0);
INSERT INTO `user_team` VALUES (18, 21, 1);
INSERT INTO `user_team` VALUES (28, 1, 1);
INSERT INTO `user_team` VALUES (34, 1, 1);
INSERT INTO `user_team` VALUES (35, 1, 1);
INSERT INTO `user_team` VALUES (37, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
