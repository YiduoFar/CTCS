package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;

/**
* @author HuiBBao
* @description 针对表【team】的数据库操作Service
* @createDate 2023-02-20 20:47:28
*/
public interface TeamService extends IService<Team> {

    ResponseResult selectTeamByCaptionId(Integer teamCaptionId);

    ResponseResult selectTeamMemVoByCaptionId(Integer teamCaptionId);

    ResponseResult insertTeam(Team team);

    ResponseResult selectTeamById(Integer teamId);

    ResponseResult updateTeamById(Team team);

    ResponseResult selectTeamDetailVoById(Integer teamId);

    ResponseResult selectTeamRecruit();

    ResponseResult selectTeamRencentlyJoinedByUserId(Integer userId);

    ResponseResult selectTeamByKeyword(QueryVo queryVo);

    ResponseResult selectTeamStar();

    ResponseResult deleteTeamById(Team team);
}
