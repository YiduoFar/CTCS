package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.TeamActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.TeamActivityDeleteVo;

/**
* @author HuiBBao
* @description 针对表【team_activity】的数据库操作Service
* @createDate 2023-02-27 00:28:02
*/
public interface TeamActivityService extends IService<TeamActivity> {

    ResponseResult deleteTeamActivity(TeamActivityDeleteVo teamActivityDeleteVo);

    ResponseResult selectByCaptionIdAndActivityId(Integer userId, Integer activityId);

    ResponseResult insertOne(TeamActivity teamActivity);

    ResponseResult selectTeamListByActivityIdAndStatus(Integer activityId, Integer status);

    ResponseResult updateStatusById(TeamActivity teamActivity);
}
