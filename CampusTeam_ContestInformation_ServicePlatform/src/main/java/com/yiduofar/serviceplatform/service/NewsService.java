package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author HuiBBao
* @description 针对表【news】的数据库操作Service
* @createDate 2023-03-10 22:25:10
*/
public interface NewsService extends IService<News> {

    ResponseResult selectAll();

    ResponseResult selectPage(QueryVo queryVo);

    ResponseResult selectById(Integer newsId);

    ResponseResult insertOne(News news);

    ResponseResult newsImageUpload(MultipartFile image) throws IOException;

    ResponseResult newsImageDelete(String imageUrl) throws IOException;

    ResponseResult deleteOne(News news);
}
