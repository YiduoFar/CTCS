package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.ActivityFile;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.CompetitionFile;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.ActivityMapper;
import com.yiduofar.serviceplatform.service.ActivityFileService;
import com.yiduofar.serviceplatform.mapper.ActivityFileMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.*;
import static com.yiduofar.serviceplatform.constant.SystemConstant.UPLOAD_PATH_WEB;

/**
 * @author HuiBBao
 * @description 针对表【activity_file】的数据库操作Service实现
 * @createDate 2023-02-27 16:42:27
 */
@Service
public class ActivityFileServiceImpl extends ServiceImpl<ActivityFileMapper, ActivityFile>
        implements ActivityFileService {

    @Autowired
    ActivityFileMapper activityFileMapper;

    @Autowired
    ActivityMapper activityMapper;

    /**
     * 上传活动文件
     * @param files
     * @param activityId
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadActivityFile(MultipartFile[] files, Integer activityId) throws IOException {
        /**
         * 根据竞赛id、类型上传竞赛文件
         * @param files
         * @param competitionId
         * @param type
         * @return
         * @throws IOException
         */
        if (files.length == 0) {
            throw new GlobalException(ResponseResultEnum.FILE_ERROR);
        }
        List<ActivityFile> res = new ArrayList<>();
        /**
         * 上传文件
         */
        for (MultipartFile file : files) {
            File image = new File(FILE_UPLOAD_PATH_REAL2 + activityId + "_" + file.getOriginalFilename());
            if (!image.getParentFile().exists()) {
                image.getParentFile().mkdirs();
            }
            file.transferTo(image);
            /**
             * 存储数据库
             */
            ActivityFile activityFile = new ActivityFile();
            activityFile.setActivityId(activityId);
            activityFile.setActivityFileName(file.getOriginalFilename());
            activityFile.setActivityFileAnnounceTime(new Date());
            String filePath2 = UPLOAD_PATH_WEB + activityId + "_" + file.getOriginalFilename();
            activityFile.setActivityFileUrl(filePath2);

            ActivityFile activityFile2 = activityFileMapper.selectOne(new QueryWrapper<ActivityFile>()
                    .eq("activity_id", activityId)
                    .eq("activity_file_name", file.getOriginalFilename()));
            // 存在，覆盖
            if (!Objects.isNull(activityFile2)) {
                activityFile.setActivityFileId(activityFile2.getActivityFileId());
                int count = activityFileMapper.updateById(activityFile);
                if (count != 1) {
                    throw new GlobalException(ResponseResultEnum.FILE_ERROR);
                }
            } else {
                // 不存在 插入
                int count = activityFileMapper.insert(activityFile);
                if (count != 1) {
                    throw new GlobalException(ResponseResultEnum.FILE_ERROR);
                }
                res.add(activityFile);
            }
        }
        return ResponseResult.success(res);
    }


    /**
     * 上传活动封面
     * @param files
     * @param activityId
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadActivityCover(MultipartFile[] files, Integer activityId) throws IOException {
        if (files.length != 1) {
            throw new GlobalException(ResponseResultEnum.FILE_ERROR);
        }
        /**
         * 上传文件
         */
        MultipartFile file = files[0];
        // 获取后缀
        System.out.println(file.getOriginalFilename());
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String suffix = split[split.length - 1];
        File image = new File(COVER_UPLOAD_PATH_REAL + "activity_" + activityId + "." + suffix);
        if (!image.getParentFile().exists()) {
            image.getParentFile().mkdirs();
        }
        file.transferTo(image);
        /**
         * 存储数据库
         */
        Activity activity = activityMapper.selectById(activityId);
        activity.setActivityCover(UPLOAD_PATH_WEB + "activity_" + activityId + "." + suffix);
        int update = activityMapper.updateById(activity);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }
}





