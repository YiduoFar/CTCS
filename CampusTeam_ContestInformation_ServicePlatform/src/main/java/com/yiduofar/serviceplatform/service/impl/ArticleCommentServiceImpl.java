package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.Article;
import com.yiduofar.serviceplatform.domain.ArticleComment;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.ArticleMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.ArticleCommentService;
import com.yiduofar.serviceplatform.mapper.ArticleCommentMapper;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.vo.CommentVo;
import com.yiduofar.serviceplatform.vo.CommentVo2;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_ARTICLE;

/**
 * @author HuiBBao
 * @description 针对表【article_comment】的数据库操作Service实现
 * @createDate 2023-03-07 15:47:55
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
        implements ArticleCommentService {

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageService messageService;

    /**
     * 根据帖子id查询评论Vo
     *
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult selectCommentVoByArticleId(Integer articleId) {
        List<ArticleComment> comments = articleCommentMapper.selectList(new QueryWrapper<ArticleComment>()
                .eq("to_article_id", articleId)
                .eq("to_comment_id", -1));
        if (CollectionUtils.isEmpty(comments)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        List<CommentVo> commentVoList = new ArrayList<>();
        // 一级评论循环
        for (ArticleComment comment : comments) {
            // 一级评论作者
            User fromUser = userMapper.selectById(comment.getFromUserId());
            // 查询二级评论
            List<ArticleComment> comments2 = articleCommentMapper.selectList(new QueryWrapper<ArticleComment>()
                    .eq("to_comment_id", comment.getCommentId()));
            List<CommentVo2> comment2VoList = new ArrayList<>();
            // 二级评论循环
            for (ArticleComment c : comments2) {
                // 二级评论 作者
                User user = userMapper.selectById(c.getFromUserId());
                // 二级评论 装填Vo
                CommentVo2 commentVo2 = new CommentVo2(user, c);
                comment2VoList.add(commentVo2);
            }
            // 一级评论 装填Vo
            CommentVo commentVo = new CommentVo(fromUser, comment, comment2VoList, false);
            commentVoList.add(commentVo);
        }
        return ResponseResult.success(commentVoList);
    }

    /**
     * 新增评论
     *
     * @param comment
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertOne(ArticleComment comment) {
        comment.setCommentCreateTime(new Date());
        // 插入评论
        int insert = articleCommentMapper.insert(comment);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 帖子评论+1
        Article article = articleMapper.selectById(comment.getToArticleId());
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("article_comment_num = article_comment_num + 1")
                .eq("article_id", article.getArticleId());
        int update = articleMapper.update(article, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        User user = userMapper.selectById(comment.getFromUserId());
        if (Objects.isNull(user)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        String content = "";
        if (comment.getToCommentId() == null) {
            // 此评论是评论帖子的
            content = "用户 " + user.getUserName() + "评论了你的帖子: " + article.getArticleTitle();
            messageService.sendMessageToUser(article.getArticleAuthorId(), content, TO_ARTICLE + article.getArticleId(), 2);
        } else {
            // 此评论是回复评论的
            ArticleComment toComment = articleCommentMapper.selectById(comment.getToCommentId());
            if (Objects.isNull(toComment)) {
                throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
            }
            content = "用户 " + user.getUserName() + "回复了你的评论。帖子(" + article.getArticleTitle() + ")";
            messageService.sendMessageToUser(toComment.getFromUserId(), content, TO_ARTICLE + article.getArticleId(), 2);
        }
        return ResponseResult.success();
    }

    /**
     * 点赞评论
     *
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult doLikeById(Integer commentId) {
        ArticleComment comment = articleCommentMapper.selectById(commentId);
        UpdateWrapper<ArticleComment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("comment_like_num = comment_like_num + 1")
                .eq("comment_id", commentId);
        int update = articleCommentMapper.update(comment, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 点踩评论
     *
     * @param commentId
     * @return
     */
    @Override
    public ResponseResult doTrampleById(Integer commentId) {
        ArticleComment comment = articleCommentMapper.selectById(commentId);
        UpdateWrapper<ArticleComment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("comment_trample_num = comment_trample_num + 1")
                .eq("comment_id", commentId);
        int update = articleCommentMapper.update(comment, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }
}




