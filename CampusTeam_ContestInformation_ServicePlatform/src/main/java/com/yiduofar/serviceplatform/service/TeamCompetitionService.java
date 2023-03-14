package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.TeamCompetition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.TeamCompetitionDeleteVo;

/**
* @author HuiBBao
* @description 针对表【team_competition】的数据库操作Service
* @createDate 2023-02-20 20:40:18
*/
public interface TeamCompetitionService extends IService<TeamCompetition> {

    ResponseResult deleteTeamCompetition(TeamCompetitionDeleteVo teamCompetitionDeleteVo);

    ResponseResult selectTeamListByCompetitionIdAndStatus(Integer competitionId, Integer status);

    ResponseResult updateStatusById(TeamCompetition teamCompetition);

    ResponseResult insertOne(TeamCompetition teamCompetition);

    ResponseResult selectByCaptionIdAndCompetitionId(Integer userId, Integer competitionId);
}

