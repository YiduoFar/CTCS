package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.constant.SystemConstant;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.CompetitionFile;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.CompetitionMapper;
import com.yiduofar.serviceplatform.service.CompetitionFileService;
import com.yiduofar.serviceplatform.mapper.CompetitionFileMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.*;

/**
 * @author HuiBBao
 * @description 针对表【competition_file】的数据库操作Service实现
 * @createDate 2023-02-14 14:57:51
 */
@Service
@Slf4j
public class CompetitionFileServiceImpl extends ServiceImpl<CompetitionFileMapper, CompetitionFile>
        implements CompetitionFileService {

    @Autowired
    CompetitionFileMapper competitionFileMapper;

    @Autowired
    CompetitionMapper competitionMapper;

    /**
     * 根据竞赛id、类型上传竞赛文件
     * @param files
     * @param competitionId
     * @param type
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadCompetitionFile(MultipartFile[] files, Integer competitionId, Integer type) throws IOException {
        Competition competition = competitionMapper.selectById(competitionId);
        // 若文件上传关闭 不允许上传报名文件 其余类型文件正常上传
        if (type == 2 && competition.getCompetitionFileUploadIsOpen() == 0) {
            throw new GlobalException(ResponseResultEnum.FILE_UPLOAD_CLOSE_ERROR);
        }
        if (files.length == 0) {
            throw new GlobalException(ResponseResultEnum.FILE_ERROR);
        }
        List<CompetitionFile> res = new ArrayList<>();
        /**
         * 上传文件
         */
        for (MultipartFile file : files) {
            String filePath = FILE_UPLOAD_PATH_REAL;
            //  + competitionId + "_" + type + 防止不同竞赛的同名文件 和同竞赛下不同文件类型的同名文件 被覆盖
            File image = new File(filePath + competitionId + "_" + type + file.getOriginalFilename());
            if (!image.getParentFile().exists()) {
                image.getParentFile().mkdirs();
            }
            file.transferTo(image);
            /**
             * 存储数据库
             */
            CompetitionFile competitionFile = new CompetitionFile();
            competitionFile.setCompetitionId(competitionId);
            competitionFile.setCompetitionFileType(type);
            competitionFile.setCompetitionFileName(file.getOriginalFilename());
            competitionFile.setCompetitionFileAnnounceTime(new Date());
            //  + competitionId + "_" + type + 防止不同竞赛的同名文件 和同竞赛下不同文件类型的同名文件 被覆盖
            String filePath2 = SystemConstant.UPLOAD_PATH_WEB + competitionId + "_" + type + file.getOriginalFilename();
            competitionFile.setCompetitionFileUrl(filePath2);

            // 同竞赛下的文件 同名 同类型 可覆盖 不同竞赛下的同名文件不影响
            QueryWrapper queryWrapper = new QueryWrapper<>()
                    .eq("competition_id", competitionId)
                    .eq("competition_file_type", type)
                    .eq("competition_file_name", file.getOriginalFilename());
            CompetitionFile competitionFile2 = competitionFileMapper.selectOne(queryWrapper);
            // 存在，覆盖
            if (!Objects.isNull(competitionFile2)) {
                competitionFile.setCompetitionFileId(competitionFile2.getCompetitionFileId());
                int count = competitionFileMapper.updateById(competitionFile);
                if (count != 1) {
                    throw new GlobalException(ResponseResultEnum.FILE_ERROR);
                }
            } else {
                // 不存在 插入
                int count = competitionFileMapper.insert(competitionFile);
                if (count != 1) {
                    throw new GlobalException(ResponseResultEnum.FILE_ERROR);
                }
                res.add(competitionFile);
            }
        }
        return ResponseResult.success(res);
    }

    /**
     * 根据竞赛id、文件类型查询文件路径
     * @param competitionId
     * @param competitionFileType
     * @return
     */
    @Override
    public ResponseResult selectFilePath(Integer competitionId, Integer competitionFileType) {
        QueryWrapper<CompetitionFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id", competitionId)
                .eq("competition_file_type", competitionFileType);
        List<CompetitionFile> list = competitionFileMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(ResponseResultEnum.NULL_FILE);
        }
        List<String> res = new ArrayList<>();
        for (CompetitionFile file : list) {
            res.add(file.getCompetitionFileUrl());
        }
        return ResponseResult.success(res);
    }

    /**
     * 根据竞赛id和类型查询文件
     * @param competitionId
     * @param competitionFileType
     * @return
     */
    @Override
    public ResponseResult selectCompetitionFileByCompetitionIdAndType(Integer competitionId, Integer competitionFileType) {
        QueryWrapper<CompetitionFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id", competitionId)
                .eq("competition_file_type", competitionFileType);
        List<CompetitionFile> list = competitionFileMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(ResponseResultEnum.NULL_FILE);
        }
        return ResponseResult.success(list);
    }

    /**
     * 删除文件
     * @param competitionFile
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteCompetitionFileById(CompetitionFile competitionFile) throws IOException {
        log.info(competitionFile.toString());
        // 删除数据库
        int count = competitionFileMapper.deleteById(competitionFile.getCompetitionFileId());
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DEL_ERROR);
        }
        // 删除文件
        String fileUploadPathReal = SystemConstant.FILE_UPLOAD_PATH_REAL;
        String fileName = competitionFile.getCompetitionFileUrl().split("s/")[1];
        Path path = Paths.get(fileUploadPathReal + fileName);
        Files.delete(path);   //返回值void
        return ResponseResult.success();
    }

    /**
     * 根据竞赛id上传封面图片
     * @param files
     * @param competitionId
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadCompetitionCover(MultipartFile[] files, Integer competitionId) throws IOException {
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
        File image = new File(COVER_UPLOAD_PATH_REAL + "competition_" + competitionId + "." + suffix);
        if (!image.getParentFile().exists()) {
            image.getParentFile().mkdirs();
        }
        file.transferTo(image);
        /**
         * 存储数据库
         */
        Competition competition = competitionMapper.selectById(competitionId);
        competition.setCompetitionCover(UPLOAD_PATH_WEB + "competition_" + competitionId + "." + suffix);
        int update = competitionMapper.updateById(competition);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }
}




