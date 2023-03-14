package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 帖子id
     */
    @TableId(type = IdType.AUTO)
    private Integer articleId;

    /**
     * 作者id
     */
    private Integer articleAuthorId;

    /**
     * 题目
     */
    private String articleTitle;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date articlePublishDate;

    /**
     * 摘要
     */
    private String articleDigest;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 标签
     */
    private String articleLabel;

    /**
     * 访问量
     */
    private Integer articlePageView;

    /**
     * 点赞量
     */
    private Integer articleLikeNum;

    /**
     * 点踩量
     */
    private Integer articleTrampleNum;

    /**
     * 评论数
     */
    private Integer articleCommentNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}