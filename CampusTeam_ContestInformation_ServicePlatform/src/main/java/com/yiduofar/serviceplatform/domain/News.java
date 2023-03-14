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
 * @TableName news
 */
@TableName(value ="news")
@Data
public class News implements Serializable {
    /**
     * 新闻id
     */
    @TableId(type = IdType.AUTO)
    private Integer newsId;

    /**
     * 题目
     */
    @NotEmpty
    private String newsTitle;

    /**
     * 摘要
     */
    @NotEmpty
    private String newsDigest;

    /**
     * 内容
     */
    @NotEmpty
    private String newsContent;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date newsPublishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}