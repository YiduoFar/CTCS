package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
* @author HuiBBao
* @description 针对表【article】的数据库操作Service
* @createDate 2023-03-06 15:55:48
*/
public interface ArticleService extends IService<Article> {

    ResponseResult selectArticleByAuthorId(Integer authorId);

    ResponseResult insertArticle(Article article);

    ResponseResult selectArticleById(Integer articleId);

    ResponseResult doLikeById(Integer articleId);

    ResponseResult doTrampleById(Integer articleId);

    ResponseResult doAddPageView(Integer articleId);

    ResponseResult deleteOne(Article article);

    ResponseResult selectArticleByKeyword(QueryVo queryVo);

    ResponseResult articleImageUpload(MultipartFile image) throws IOException;

    ResponseResult articleImageDelete(String imageUrl) throws IOException;
}
