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
 * @TableName team
 */
@TableName(value ="team")
@Data
public class Team implements Serializable {
    /**
     * 队伍id
     */
    @TableId(type = IdType.AUTO)
    private Integer teamId;

    /**
     * 队伍logo
     */
    @NotEmpty
    private String teamLogo;

    /**
     * 队伍名称
     */
    @NotEmpty
    private String teamName;

    /**
     * 队伍宣言
     */
    @NotEmpty
    private String teamIntro;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date teamCreateTime;

    /**
     * 队长id
     */
    @NotNull
    private Integer teamCaptionId;

    /**
     * 队伍人数上限
     */
    @NotNull
    private Integer teamMemberNumMax;

    /**
     * 目前人数
     */
    private Integer teamMemberNum;

    /**
     * 星级（评分）
     */
    private Double teamStar;

    /**
     * 标签（数组）
     */
    private String teamLabel;

    /**
     * 状态，0不招新，1招新
     */
    private Integer teamStatus;

    /**
     * 冗余字段
     * 队长昵称
     */
    private String teamCaptionUserName;

    /**
     * 冗余字段
     * 队长姓名
     */
    private String teamCaptionRealName;

    /**
     * 队伍所属学院
     */
    private String teamCollege;

    /**
     * 队伍所属专业
     */
    private String teamSpecialty;

    /**
     * 邀请码
     */
    private String teamInviteCode;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}