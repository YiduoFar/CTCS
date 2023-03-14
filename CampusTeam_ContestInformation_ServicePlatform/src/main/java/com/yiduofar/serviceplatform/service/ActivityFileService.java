package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.ActivityFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author HuiBBao
* @description 针对表【activity_file】的数据库操作Service
* @createDate 2023-02-27 16:42:27
*/
public interface ActivityFileService extends IService<ActivityFile> {

    ResponseResult uploadActivityFile(MultipartFile[] files, Integer activityId) throws IOException;

    ResponseResult uploadActivityCover(MultipartFile[] files, Integer activityId) throws IOException;
}
