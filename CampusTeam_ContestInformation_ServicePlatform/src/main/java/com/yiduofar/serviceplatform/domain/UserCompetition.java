package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName user_competition
 */
@TableName(value ="user_competition")
@Data
public class UserCompetition implements Serializable {

    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 竞赛id
     */
    private Integer competitionId;

    /**
     * 状态（0未确认，1确认）
     */
    private Integer status;

    /**
     *
     */
//    private Date time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}