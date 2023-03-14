package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @TableName competition
 */
@TableName(value = "competition")
@Data
public class Competition implements Serializable {

    /**
     * 竞赛id
     */
    @TableId(type = IdType.AUTO)
    private Integer competitionId;

    /**
     * 竞赛名称
     */
    @NotEmpty
    private String competitionName;

    /**
     * 已报名数量
     */
    private Integer competitionJoinedNum;

    /**
     * 类型，0工科竞赛，1文科竞赛
     */
    private Integer competitionType;

    /**
     * 是否需要组队报名，0个人，1组队
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
    private Integer competitionAudience;

    /**
     * 具体受众
     */
    private String competitionAudienceName;

    /**
     * 目前进行的步骤
     */
    private Integer competitionStageNow;

    /**
     * 步骤（JSON化的步骤对象的数组）
     */
    @NotEmpty
    private String competitionStage;

    /**
     * 承办方名称
     */
    @NotEmpty
    private String competitionUndertaker;

    /**
     * 校内承办单位名称
     */
    @NotEmpty
    private String competitionUndertaker2;

    /**
     * 竞赛发布、负责人id
     */
    private Integer competitionUndertaker3Id;

    /**
     * 指导老师名单（JSON化的指导老师对象的数组）
     */
    private String competitionAdvisorList;

    /**
     * 内容（JSON化的内容对象的数组）
     */
    @NotEmpty
    private String competitionInformationList;

    /**
     * 竞赛开始时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date competitionBeginTime;

    /**
     * 竞赛结束时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date competitionEndTime;

    /**
     * 预计综测加分
     */
    @NotNull
    private Integer competitionExtraPoints;

    /**
     * 状态，0已结束，1进行中
     */
    private Integer competitionStatus;

    /**
     * 文件上传开关
     */
    private Integer competitionFileUploadIsOpen;

    /**
     * 报名开关
     */
    private Integer competitionJoinedIsOpen;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date competitionPublishDate;

    /**
     * 封面
     */
    private String competitionCover;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}