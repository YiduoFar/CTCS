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
 * @TableName article_comment
 */
@TableName(value ="article_comment")
@Data
public class ArticleComment implements Serializable {
    /**
     * 评论id
     */
    @TableId(type = IdType.AUTO)
    private Integer commentId;

    /**
     * 被评论的帖子id
     */
    private Integer toArticleId;

    /**
     * 被评论的评论的id
     */
    private Integer toCommentId;

    /**
     * 评论人id
     */
    private Integer fromUserId;

    /**
     * 内容
     */
    private String commentContent;

    /**
     * 时间
     */
    private Date commentCreateTime;

    /**
     * 点赞数量
     */
    private Integer commentLikeNum;

    /**
     * 点踩数量
     */
    private Integer commentTrampleNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}