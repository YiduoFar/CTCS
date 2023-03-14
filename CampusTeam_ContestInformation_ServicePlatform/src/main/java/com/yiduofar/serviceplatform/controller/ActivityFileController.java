package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.service.ActivityFileService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * (ActivityFile)表控制层
 *
 * @author makejava
 * @since 2023-02-27 16:42:45
 */
@RestController
public class ActivityFileController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private ActivityFileService activityFileService;

    /**
     * 根据竞赛id、类型上传竞赛文件
     * @param files
     * @param competitionId
     * @param type
     * @return
     * @throws IOException
     */
    @PostMapping("/activity/file/upload")
    public ResponseResult uploadActivityFile(@RequestParam(value = "file") MultipartFile[] files,
                                             @RequestParam(value = "activityId") Integer activityId) throws IOException {
        return activityFileService.uploadActivityFile(files, activityId);
    }

    /**
     * 根据活动id上传封面图片
     * @param files
     * @param activityId
     * @return
     * @throws IOException
     */
    @PostMapping("/activity/file/uploadCover")
    public ResponseResult uploadActivityCover(@RequestParam(value = "file") MultipartFile[] files,
                                                 @RequestParam(value = "activityId") Integer activityId) throws IOException {
        return activityFileService.uploadActivityCover(files, activityId);
    }

}

