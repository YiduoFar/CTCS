package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.CompetitionFile;
import com.yiduofar.serviceplatform.service.CompetitionFileService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (CompetitionFile)表控制层
 *
 * @author makejava
 * @since 2023-02-14 13:49:11
 */
@RestController
@Slf4j
public class CompetitionFileController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CompetitionFileService competitionFileService;

    /**
     * 根据竞赛id、类型上传竞赛文件
     * @param files
     * @param competitionId
     * @param type
     * @return
     * @throws IOException
     */
    @PostMapping("/competition/file/upload")
    public ResponseResult uploadCompetitionFile(@RequestParam(value = "file") MultipartFile[] files,
                                                     @RequestParam(value = "competitionId") Integer competitionId,
                                                     @RequestParam(value = "type")Integer type) throws IOException {
        return competitionFileService.uploadCompetitionFile(files, competitionId, type);
    }

    /**
     * 根据竞赛id上传封面图片
     * @param files
     * @param competitionId
     * @return
     * @throws IOException
     */
    @PostMapping("/competition/file/uploadCover")
    public ResponseResult uploadCompetitionCover(@RequestParam(value = "file") MultipartFile[] files,
                                                @RequestParam(value = "competitionId") Integer competitionId) throws IOException {
        return competitionFileService.uploadCompetitionCover(files, competitionId);
    }

    /**
     * 根据竞赛id、文件类型查询文件路径
     * @param competitionId
     * @param competitionFileType
     * @return
     */
    @GetMapping("/competition/file/selectFilePath")
    public ResponseResult selectFilePath(@RequestParam Integer competitionId,
                                         @RequestParam Integer competitionFileType) {
        return competitionFileService.selectFilePath(competitionId, competitionFileType);
    }

    /**
     * 根据竞赛id和类型查询文件
     * @param competitionId
     * @param competitionFileType
     * @return
     */
    @GetMapping("/competition/file/selectCompetitionFileByCompetitionIdAndType")
    public ResponseResult selectCompetitionFileByCompetitionIdAndType(@RequestParam Integer competitionId,
                                         @RequestParam Integer competitionFileType) {
        return competitionFileService.selectCompetitionFileByCompetitionIdAndType(competitionId, competitionFileType);
    }

    /**
     * 删除文件
     * @param competitionFile
     * @return
     * @throws IOException
     */
    @PostMapping("/competition/file/deleteCompetitionFileById")
    public ResponseResult deleteCompetitionFileById(CompetitionFile competitionFile) throws IOException {
        return competitionFileService.deleteCompetitionFileById(competitionFile);
    }

}

