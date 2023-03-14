package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.Message;
import com.yiduofar.serviceplatform.domain.News;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.service.NewsService;
import com.yiduofar.serviceplatform.mapper.NewsMapper;
import com.yiduofar.serviceplatform.socketio.NettySocketEventHandler;
import com.yiduofar.serviceplatform.util.UUIDUtil;
import com.yiduofar.serviceplatform.vo.NewsPageTotal;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.*;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.toNewsOrAnnouncementUrl;

/**
* @author HuiBBao
* @description 针对表【news】的数据库操作Service实现
* @createDate 2023-03-10 22:25:10
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    UUIDUtil uuidUtil;

    /**
     * 获取所有新闻
     * @return
     */
    @Override
    public ResponseResult selectAll() {
        List<News> news = newsMapper.selectList(new QueryWrapper<News>()
                .orderByDesc("news_publish_time"));
        if (CollectionUtils.isEmpty(news)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(news);
    }


    /**
     * 分页
     * 获取新闻
     * @return
     */
    @Override
    public ResponseResult selectPage(QueryVo queryVo) {
        // 封装分页对象
        Page page = new Page(queryVo.getCurrent(), queryVo.getSize());
        // 分页查询
        List<News> news = newsMapper.selectPage2(page);
        if (CollectionUtils.isEmpty(news)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(new NewsPageTotal(news, page.getTotal()));
    }

    /**
     * 根据id查询新闻
     * @param newsId
     * @return
     */
    @Override
    public ResponseResult selectById(Integer newsId) {
        News news = newsMapper.selectById(newsId);
        if (Objects.isNull(news)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(news);
    }

    /**
     * 发布新闻
     * @param news
     * @return
     */
    @Override
    public ResponseResult insertOne(News news) {
        news.setNewsPublishTime(new Date());
        int insert = newsMapper.insert(news);
        if (Objects.isNull(insert)) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        Message message = new Message();
        message.setMessageType(3);
        message.setMessageObjectType(2);
        message.setMessageTime(new Date());
        message.setMessageContent(news.getNewsTitle());
        message.setMessageUrl(toNewsOrAnnouncementUrl(news.getNewsId(), "news"));
        // socketio广播新闻
        NettySocketEventHandler.sendBroadcast(message);
        return ResponseResult.success(news.getNewsId());
    }

    /**
     * 上传新闻图片
     * @param image
     * @return
     */
    @Override
    public ResponseResult newsImageUpload(MultipartFile image) throws IOException {
        if (Objects.isNull(image)) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        // 时间戳
        long time = Calendar.getInstance().getTime().getTime();
        // 文件名
        String[] split = image.getOriginalFilename().split("\\.");
        String fileName = time + uuidUtil.create3Code() + "." + split[split.length - 1];
        // 上传
        File file = new File(FILE_UPLOAD_PATH_REAL4 + fileName);
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
    public ResponseResult newsImageDelete(String imageUrl) throws IOException {
        // 删除文件
        String fileUploadPathReal = SystemConstant.FILE_UPLOAD_PATH_REAL4;
        String fileName = imageUrl.split("s/")[1];
        Path path = Paths.get(fileUploadPathReal + fileName);
        Files.delete(path);   //返回值void
        return ResponseResult.success();
    }

    /**
     * 删除新闻
     * @param news
     * @return
     */
    @Override
    public ResponseResult deleteOne(News news) {
        int delete = newsMapper.deleteById(news.getNewsId());
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

}




