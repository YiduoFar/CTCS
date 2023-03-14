package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @TableName user_team
 */
@TableName(value ="user_team")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTeam implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 队伍id
     */
    private Integer teamId;

    /**
     * 状态 0未通过 1通过
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}