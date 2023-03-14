package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.ArticleComment;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 二级评论Vo
 * @author HuiBBao
 * @create 2023/3/7 21:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentVo2 {
    /**
     * 评论人
     */
    User fromUser;
    /**
     * 二级评论
     */
    ArticleComment comment;
}
