package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity implements Serializable {
    /**
     * 活动id
     */
    @TableId(type = IdType.AUTO)
    private Integer activityId;

    /**
     * 活动名称
     */
    @NotEmpty
    private String activityName;

    /**
     * 活动封面
     */
    private String activityCover;

    /**
     * 类型 0团建 1聚会 2运动 3社团 4户外 5游戏
     */
    private Integer activityType;

    /**
     * 活动报名是否开启，0关闭，1开启
     */
    private Integer activityJoinedIsOpen;

    /**
     * 状态，0已结束，1进行中
     */
    private Integer activityStatus;

    /**
     * 步骤（JSON化的步骤对象的数组）
     */
    @NotEmpty
    private String activityStage;

    /**
     * 内容（JSON化的内容对象的数组）
     */
    @NotEmpty
    private String activityInformationList;

    /**
     * 是否需要组队参加 0个人 1组队
     */
    private Integer isTeamUp;

    /**
     * 队伍最少人数
     */
    private Integer signUpMinTeamMemberNum;

    /**
     * 队伍最多人数
     */
    private Integer signUpMaxTeamMemberNum;

    /**
     * 受众，0面向全校，1面向学院、2面向专业、3面向个人
     */
    private Integer activityAudience;

    /**
     * 具体受众
     */
    private String activityAudienceName;

    /**
     * 已报名数量
     */
    private Integer activityJoinedNum;

    /**
     * 当前步骤
     */
    private Integer activityStageNow;

    /**
     * 发起人id
     */
    @NotNull
    private Integer activityInitiatorId;

    /**
     * 是否线上 0线下 1线上
     */
    private Integer isOnline;

    /**
     * 地点
     */
    @NotEmpty
    private String activityPlace;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date activityBeginTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date activityEndTime;

    /**
     * 活动加分
     */
    private Integer activityPoints;

    /**
     * 活动发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityPublishDate;

    /**
     * 活动简介
     */
    @NotEmpty
    private String activityIntro;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}