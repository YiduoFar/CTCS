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
 * @TableName activity_file
 */
@TableName(value ="activity_file")
@Data
public class ActivityFile implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer activityFileId;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 文件名
     */
    private String activityFileName;

    /**
     * 文件路径
     */
    private String activityFileUrl;

    /**
     * 发布时间
     */
    private Date activityFileAnnounceTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}