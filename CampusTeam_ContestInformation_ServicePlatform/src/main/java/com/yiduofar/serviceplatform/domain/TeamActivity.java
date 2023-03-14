package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName team_activity
 */
@TableName(value ="team_activity")
@Data
public class TeamActivity implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer teamId;

    /**
     * 
     */
    private Integer activityId;

    /**
     * 
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}