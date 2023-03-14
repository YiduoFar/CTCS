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

/**
 * 
 * @TableName announcement
 */
@TableName(value ="announcement")
@Data
public class Announcement implements Serializable {
    /**
     * 公告id
     */
    @TableId(type = IdType.AUTO)
    private Integer announcementId;

    /**
     * 题目
     */
    @NotEmpty
    private String announcementTitle;

    /**
     * 内容
     */
    @NotEmpty
    private String announcementContent;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date announcementPublishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}