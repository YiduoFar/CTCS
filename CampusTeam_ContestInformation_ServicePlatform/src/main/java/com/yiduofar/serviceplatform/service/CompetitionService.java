package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Competition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
* @author HuiBBao
* @description 针对表【competition】的数据库操作Service
* @createDate 2023-02-13 14:54:49
*/
public interface CompetitionService extends IService<Competition> {

    ResponseResult selectCompetitionByUserIdAndKeyWord(QueryVo queryVo);

    ResponseResult insertCompetition(Competition competition);

    ResponseResult selectCompetitionByUndertaker3Id(Integer undertaker3Id, String keyword);


    ResponseResult updateCompetitionById(Competition competition);

    ResponseResult selectCompetitionVoById(Integer competitionId);

    ResponseResult selectCompetitionById(Integer competitionId);

    ResponseResult deleteCompetitionById(Integer competitionId);

    ResponseResult selectCompetitionByKeyWord(QueryVo queryVo);

    ResponseResult selectCompetitionByStatus(QueryVo queryVo);

    ResponseResult selectSlideShowVo();

}
