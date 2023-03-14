package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.UserTeam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserTeamDeleteVo;

/**
* @author HuiBBao
* @description 针对表【user_team】的数据库操作Service
* @createDate 2023-03-02 20:52:25
*/
public interface UserTeamService extends IService<UserTeam> {

    ResponseResult deleteOne(UserTeamDeleteVo userTeamDeleteVo);

    ResponseResult selectUserListByTeamIdAndStatus(Integer teamId, Integer status);

    ResponseResult updateStatusById(UserTeam userTeam);

    ResponseResult insertOne(UserTeam userTeam);

    ResponseResult selectAndJudgeOne(Integer userId, Integer teamId);

    ResponseResult fastJoinTeam(Integer userId, Integer teamInviteCode);
}
