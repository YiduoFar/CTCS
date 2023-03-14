package com.yiduofar.serviceplatform.vo;

import lombok.*;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:13
 */

@ToString
@AllArgsConstructor
@Getter
public enum ResponseResultEnum {
    // 通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务器异常"),
    SSE_ERROR(606, "创建链接异常"),
    HTTP_STATUS_FORBIDDEN(400403, "权限不足"),
    HTTP_STATUS_UNAUTHORIZED(400404, "token失效，请重新登录"),
    NULL_DATA_ERROR(500400, "无数据"),
    // 登录模块
    LOGINED_ERROR(500209, "该用户已在其他设备登录"),
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    TOKEN_ERROR(500213, "token失效，请重新登录"),
    LOGIN_TIMEOUT_ERROR(500214, "用户未登录"),
    // 注册模块
    REG_ERROR(500310, "手机号已被注册"),
    REG_USERNAME_ERROR(500320, "昵称已存在"),
    REG_NUMBER_ERROR(500321, "学号/工号已存在"),
    REG_PHONE_ERROR(500322, "手机号已注册"),
    REG_EMAIL_ERROR(500323, "邮箱已注册"),
    // 后台模块
    NULL_USER_ERROR(500401, "用户信息为空"),
    FILE_ERROR(500402, "文件上传错误"),
    NULL_FILE(500403, "没有文件"),
    DATABASE_ERROR(500404, "数据库错误"),
    NULL_USER_LIST_ERROR(500405, "没有用户列表数据"),
    NULL_TEAM_LIST_ERROR(500406, "没有队伍列表数据"),
    EDIT_ERROR(500407, "服务器异常，编辑失败"),
    DEL_ERROR(500408, "服务器异常，删除失败"),
    MESSAGE_ERROR(500409, "消息异常"),
    NULL_MESSAGE_ERROR(500410, "无消息"),
    TEAM_MEM_NUM_ERROR(500411, "队伍人数不符合要求"),
    USER_AUDIENCE_ERROR(500412, "您所属学院不符要求"),
    USER_SPECIALTY_ERROR(500413, "您所属专业不符要求"),
    TEAM_AUDIENCE_ERROR(500414, "队伍所属学院不符要求"),
    TEAM_SPECIALTY_ERROR(500415, "队伍所属专业不符要求"),
    TEAM_NAME_ERROR(500416, "队名已存在"),
    TEAM_CREATE_ERROR(500417, "创建失败，您尚未通过实名验证"),
    TEAM_CREATE_NUM_ERROR(500418, "创建队伍数到达上限，不允许创建更多的队伍"),
    TEAM_MEM_NUM_OVERSIZE_ERROR(500419, "编辑失败，当前队伍人数超出您选择的上限"),
    TEAM_JOIN_ERROR(500420, "您已提交申请，等待队长审核"),
    TEAM_JOIN_ERROR2(500421, "您已加入该队伍"),
    TEAM_JOIN_ERROR3(500422, "您是队长"),
    USER_INITIATOR_JOIN_ERROR(500423, "您是创建人"),
    TEAM_NULL_ERROR(500424, "队伍不存在"),
    FILE_UPLOAD_CLOSE_ERROR(500425, "负责人未开启文件上传"),
    ACTIVITY_END_ERROR(500426, "活动已结束"),
    COMPETITION_END_ERROR(500426, "竞赛已结束");

    private final Integer code;
    private final String message;
}
