package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* @author HuiBBao
* @description 针对表【article_comment】的数据库操作Service
* @createDate 2023-03-07 15:47:55
*/
public interface ArticleCommentService extends IService<ArticleComment> {

    ResponseResult selectCommentVoByArticleId(Integer articleId);

    ResponseResult insertOne(ArticleComment comment);

    ResponseResult doLikeById(Integer commentId);

    ResponseResult doTrampleById(Integer commentId);

}
