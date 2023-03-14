package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.domain.News;
import com.yiduofar.serviceplatform.service.NewsService;
import com.yiduofar.serviceplatform.socketio.NettySocketEventHandler;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * (News)表控制层
 *
 * @author makejava
 * @since 2023-03-10 22:25:22
 */
@RestController
public class NewsController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private NewsService newsService;

    /**
     * 查询所有新闻
     * @return
     */
    @GetMapping("/news/selectAll")
    public ResponseResult selectAll() {
        return newsService.selectAll();
    }

    /**
     * 分页
     * 查询新闻
     * @return
     */
    @PostMapping("/show/news/selectPage")
    public ResponseResult selectPage(QueryVo queryVo) {
        return newsService.selectPage(queryVo);
    }

    /**
     * 根据id查询新闻
     * @param newsId
     * @return
     */
    @GetMapping("/news/selectById")
    public ResponseResult selectById(Integer newsId) {
        return newsService.selectById(newsId);
    }

    /**
     * 发布新闻
     * @param news
     * @return
     */
    @PostMapping("/news/insertOne")
    public ResponseResult insertOne(@Valid News news) {
        return newsService.insertOne(news);
    }

    /**
     * 上传帖子图片
     * @param image
     * @return
     */
    @PostMapping("/news/imageUpload")
    public ResponseResult newsImageUpload(@RequestParam(value = "image") MultipartFile image) throws IOException {
        return newsService.newsImageUpload(image);
    }

    /**
     * 删除帖子图片
     * @param imageUrl
     * @return
     */
    @PostMapping("/news/imageDelete")
    public ResponseResult newsImageDelete(String imageUrl) throws IOException {
        return newsService.newsImageDelete(imageUrl);
    }

    /**
     * 删除新闻
     * @param news
     * @return
     */
    @PostMapping("/news/deleteOne")
    public ResponseResult deleteOne(News news) {
        return newsService.deleteOne(news);
    }

}

