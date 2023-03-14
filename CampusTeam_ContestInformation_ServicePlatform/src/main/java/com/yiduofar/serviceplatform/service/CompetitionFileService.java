package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.CompetitionFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.mapper.CompetitionFileMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author HuiBBao
* @description 针对表【competition_file】的数据库操作Service
* @createDate 2023-02-14 14:57:51
*/
public interface CompetitionFileService extends IService<CompetitionFile> {

    ResponseResult uploadCompetitionFile(MultipartFile[] files, Integer competitionId, Integer type) throws IOException;

    ResponseResult selectFilePath(Integer competitionId, Integer competitionFileType);

    ResponseResult selectCompetitionFileByCompetitionIdAndType(Integer competitionId, Integer competitionFileType);

    ResponseResult deleteCompetitionFileById(CompetitionFile competitionFile) throws IOException;

    ResponseResult uploadCompetitionCover(MultipartFile[] files, Integer competitionId) throws IOException;

}
