package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.ArticleComment;
import com.yiduofar.serviceplatform.service.ArticleCommentService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ArticleComment)表控制层
 *
 * @author makejava
 * @since 2023-03-07 15:48:06
 */
@RestController
public class ArticleCommentController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleCommentService articleCommentService;

    /**
     * 根据帖子id查询评论Vo
     * @param articleId
     * @return
     */
    @GetMapping("/article/comment/selectCommentVoByArticleId")
    public ResponseResult selectCommentVoByArticleId(Integer articleId) {
        return articleCommentService.selectCommentVoByArticleId(articleId);
    }

    /**
     * 新增一级二级评论
     * @param comment
     * @return
     */
    @PostMapping("/article/comment/insertOne")
    public ResponseResult insertOne(ArticleComment comment) {
        return articleCommentService.insertOne(comment);
    }

    /**
     * 点赞评论
     * @param commentId
     * @return
     */
    @GetMapping("/article/comment/doLikeById")
    public ResponseResult doLikeById(Integer commentId) {
        return articleCommentService.doLikeById(commentId);
    }

    /**
     * 点踩评论
     * @param commentId
     * @return
     */
    @GetMapping("/article/comment/doTrampleById")
    public ResponseResult doTrampleById(Integer commentId) {
        return articleCommentService.doTrampleById(commentId);
    }

}

