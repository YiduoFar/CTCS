package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.Article;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.TeamMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.ArticleService;
import com.yiduofar.serviceplatform.mapper.ArticleMapper;
import com.yiduofar.serviceplatform.util.UUIDUtil;
import com.yiduofar.serviceplatform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.yiduofar.serviceplatform.constant.SystemConstant.*;

/**
 * @author HuiBBao
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2023-03-06 15:55:48
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UUIDUtil uuidUtil;


    /**
     * 根据作者查询帖子
     *
     * @param authorId
     * @return
     */
    @Override
    public ResponseResult selectArticleByAuthorId(Integer authorId) {
        List<Article> articles = articleMapper.selectList(new QueryWrapper<Article>()
                .eq("article_author_id", authorId)
                .orderByDesc("article_publish_date"));
        if (CollectionUtils.isEmpty(articles)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(articles);
    }

    /**
     * 发布帖子
     *
     * @param article
     * @return
     */
    @Override
    public ResponseResult insertArticle(Article article){
        // 发布时间
        article.setArticlePublishDate(new Date());
        int insert = articleMapper.insert(article);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 根据帖子id查询帖子
     *
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult selectArticleById(Integer articleId) {
        // 帖子
        Article article = articleMapper.selectById(articleId);
        if (Objects.isNull(article)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        User user = userMapper.selectById(article.getArticleAuthorId());
        // 作者Vo
        AuthorVo authorVo = new AuthorVo();
        authorVo.setUserId(user.getId());
        authorVo.setUserName(user.getUserName());
        authorVo.setUserHead(user.getUserHead());
        Integer teamNum = teamMapper.selectCount(new QueryWrapper<Team>()
                .eq("team_caption_id", user.getId()));
        authorVo.setTeamNum(teamNum);
        Integer articleNum = articleMapper.selectCount(new QueryWrapper<Article>()
                .eq("article_author_id", user.getId()));
        authorVo.setArticleNum(articleNum);
        return ResponseResult.success(new ArticleVo(article, authorVo));
    }

    /**
     * 点赞
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult doLikeById(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("article_like_num = article_like_num + 1")
                .eq("article_id", articleId);
        int update = articleMapper.update(article, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 点踩
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult doTrampleById(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("article_trample_num = article_trample_num + 1")
                .eq("article_id", articleId);
        int update = articleMapper.update(article, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 增加访问量
     * @param articleId
     * @return
     */
    @Override
    public ResponseResult doAddPageView(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("article_page_view = article_page_view + 1")
                .eq("article_id", articleId);
        int update = articleMapper.update(article, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 删除帖子
     * @param article
     * @return
     */
    @Override
    public ResponseResult deleteOne(Article article) {
        int delete = articleMapper.deleteById(article.getArticleId());
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 分页
     * 根据关键字查询帖子列表
     * @param queryVo
     * @return
     */
    @Override
    public ResponseResult selectArticleByKeyword(QueryVo queryVo) {
        // 封装分页对象
        Page<Article> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        List<Article> articles = articleMapper.selectPage2(page, new QueryWrapper<>()
                .like("article_title", queryVo.getKeyword()));
        if (CollectionUtils.isEmpty(articles)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        Long total = page.getTotal();
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            User user = userMapper.selectById(article.getArticleAuthorId());
            articleVos.add(new ArticleVo(article, new AuthorVo(user.getId(), user.getUserName(), user.getUserHead(), 0, 0)));
        }
        return ResponseResult.success(new ArticlePageTotal(articleVos, total));
    }

    /**
     * 上传帖子图片
     * @param image
     * @return
     */
    @Override
    public ResponseResult articleImageUpload(MultipartFile image) throws IOException {
        if (Objects.isNull(image)) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        // 时间戳
        long time = Calendar.getInstance().getTime().getTime();
        // 文件名
        String[] split = image.getOriginalFilename().split("\\.");
        String fileName = time + uuidUtil.create3Code() + "." + split[split.length - 1];
        // 上传
        File file = new File(FILE_UPLOAD_PATH_REAL3 + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        // 返回路径
        String resPath = UPLOAD_PATH_WEB + fileName;
        return ResponseResult.success(resPath);
    }

    /**
     * 删除帖子图片
     * @param imageUrl
     * @return
     */
    @Override
    public ResponseResult articleImageDelete(String imageUrl) throws IOException {
        // 删除文件
        String fileUploadPathReal = SystemConstant.FILE_UPLOAD_PATH_REAL3;
        String fileName = imageUrl.split("s/")[1];
        Path path = Paths.get(fileUploadPathReal + fileName);
        Files.delete(path);   //返回值void
        return ResponseResult.success();
    }

}




