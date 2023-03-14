package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_activity
 */
@TableName(value ="user_activity")
@Data
public class UserActivity implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 状态（0未确认，1确认）
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}