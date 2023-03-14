package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/7 18:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleVo {
    /**
     * 帖子
     */
    Article article;
    /**
     * 作者
     */
    AuthorVo authorVo;
}
