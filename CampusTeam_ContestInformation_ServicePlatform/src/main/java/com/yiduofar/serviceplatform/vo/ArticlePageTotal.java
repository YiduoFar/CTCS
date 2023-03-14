package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/9 0:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticlePageTotal {
    List<ArticleVo> page;
    Long total;
}
