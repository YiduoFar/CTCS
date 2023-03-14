package com.yiduofar.serviceplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.Article;
import com.yiduofar.serviceplatform.service.ArticleService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2023-03-06 15:56:14
 */
@RestController
public class ArticleController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * 根据作者id查询帖子
     *
     * @param authorId
     * @return
     */
    @GetMapping("/article/selectArticleByAuthorId")
    public ResponseResult selectArticleByAuthorId(Integer authorId) {
        return articleService.selectArticleByAuthorId(authorId);
    }

    /**
     * 根据帖子id查询帖子
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/selectArticleById")
    public ResponseResult selectArticleById(Integer articleId) {
        return articleService.selectArticleById(articleId);
    }

    /**
     * 发布帖子
     *
     * @param article
     * @return
     */
    @PostMapping("/article/insertArticle")
    public ResponseResult insertArticle(Article article) {
        return articleService.insertArticle(article);
    }

    /**
     * 点赞
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/doLikeById")
    public ResponseResult doLikeById(Integer articleId) {
        return articleService.doLikeById(articleId);
    }

    /**
     * 点踩
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/doTrampleById")
    public ResponseResult doTrampleById(Integer articleId) {
        return articleService.doTrampleById(articleId);
    }

    /**
     * 增加访问量
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/doAddPageView")
    public ResponseResult doAddPageView(Integer articleId) {
        return articleService.doAddPageView(articleId);
    }

    /**
     * 删除帖子
     *
     * @param article
     * @return
     */
    @PostMapping("/article/deleteOne")
    public ResponseResult deleteOne(Article article) {
        return articleService.deleteOne(article);
    }

    /**
     * 分页
     * 根据关键字查询帖子列表
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/show/article/selectArticleByKeyword")
    public ResponseResult selectArticleByKeyword(QueryVo queryVo) {
        return articleService.selectArticleByKeyword(queryVo);
    }

    /**
     * 上传帖子图片
     * @param image
     * @return
     */
    @PostMapping("/article/articleImageUpload")
    public ResponseResult articleImageUpload(@RequestParam(value = "image") MultipartFile image) throws IOException {
        return articleService.articleImageUpload(image);
    }

    /**
     * 删除帖子图片
     * @param imageUrl
     * @return
     */
    @PostMapping("/article/articleImageDelete")
    public ResponseResult articleImageDelete(String imageUrl) throws IOException {
        return articleService.articleImageDelete(imageUrl);
    }

}

