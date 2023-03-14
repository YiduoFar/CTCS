package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.ArticleComment;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 一级评论Vo
 * @author HuiBBao
 * @create 2023/3/7 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentVo {
    /**
     * 评论人
     */
    User fromUser;
    /**
     * 一级评论Vo
     */
    ArticleComment comment;
    /**
     * 二级评论Vo列表
     */
    List<CommentVo2> comment2VoList;
    /**
     * 回复显示控制
     */
    Boolean replyShow;
}
