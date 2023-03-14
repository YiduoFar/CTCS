package com.yiduofar.serviceplatform.constant;

/**
 * @author HuiBBao
 * @create 2023/2/12 19:53
 */
public final class SystemConstant {

    /**
     * 用户头像上传根路径
     */
    public static final String USERHEAD_UPLOAD_PATH_REAL = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/img/userhead/";

    /**
     * 封面上传根路径
     */
    public static final String COVER_UPLOAD_PATH_REAL = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/img/cover/";

    /**
     * 文件上传根路径（竞赛）
     */
    public static final String FILE_UPLOAD_PATH_REAL = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/file/";

    /**
     * 文件上传根路径2（活动）
     */
    public static final String FILE_UPLOAD_PATH_REAL2 = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/file2/";

    /**
     * 文件上传根路径3（帖子图片）
     */
    public static final String FILE_UPLOAD_PATH_REAL3 = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/img/article/";

    /**
     * 文件上传根路径4（新闻图片）
     */
    public static final String FILE_UPLOAD_PATH_REAL4 = "D:/Project/校园组队与竞赛信息服务平台/CampusTeam_ContestInformation_ServicePlatform/src/main/resources/static/img/news/";

    /**
     * 文件存储根路径
     */
    public static final String UPLOAD_PATH_WEB = "http://localhost:8088/resources/";

    /**
     * 用户头像图片后缀
     */
    public static final String USERHEAD_SUFFIX = ".jpg";

    /**
     * 消息相关
     */
    public static final class MessageTip {
        /**
         * 竞赛
         */
        public static final String[] USER_COMPETITION_DELETE_TIP = new String[]{"您已经退出竞赛：", "参赛申请被婉拒：", "参赛资格被取消："};
        public static final String[] TEAM_COMPETITION_DELETE_TIP = new String[]{"您的队伍已退出竞赛：", "队伍参赛申请被婉拒：", "队伍参赛资格被取消："};
        /**
         * 活动
         */
        public static final String[] USER_ACTIVITY_DELETE_TIP = new String[]{"您已经退出活动：", "活动报名被婉拒：", "活动资格被取消："};
        public static final String[] TEAM_ACTIVITY_DELETE_TIP = new String[]{"您的队伍已退出活动：", "队伍活动报名被婉拒：", "队伍活动资格被取消："};
        /**
         * 队伍
         */
        public static final String[] USER_TEAM_DELETE_TIP = new String[]{"成功离开队伍：", "队伍申请被婉拒：", "组队资格被取消："};
    }

    /**
     * 消息路径
     */
    public static final class MessageUrl {

        public static final String TO_ARTICLE = "article_details?articleId=";

        public static final String TO_COMPETITION = "competition_details?competitionId=";

        public static final String TO_ACTIVITY = "activity_details?activityId=";

        public static final String TO_USER = "user_details?userId=";

        public static final String TO_TEAM = "team_details?teamId=";

        public static final String TO_BACKSTAGE = "backstage?focusIndex=";

        public static String toNewsOrAnnouncementUrl(Integer id, String type) {
            return "n_a_details?id=" + id + "&type=" + type;
        }
    }

}
