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
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message implements Serializable {
    /**
     * 消息id
     */
    @TableId(type = IdType.AUTO)
    private Integer messageId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 内容
     */
    private String messageContent;

    /**
     * 时间
     */
    private Date messageTime;

    /**
     * 跳转页面url
     */
    private String messageUrl;

    /**
     * 类型 0通知 1申请
     */
    private Integer messageType;

    private Integer teamId;

    private Integer messageObjectType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}