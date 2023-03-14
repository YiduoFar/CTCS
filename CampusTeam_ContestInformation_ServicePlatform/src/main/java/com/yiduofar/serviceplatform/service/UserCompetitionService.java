package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserCompetitionDeleteVo;

/**
* @author HuiBBao
* @description 针对表【user_competition】的数据库操作Service
* @createDate 2023-02-14 15:30:09
*/
public interface UserCompetitionService extends IService<UserCompetition> {

    ResponseResult deleteUserCompetition(UserCompetitionDeleteVo userCompetitionDeleteVo);

    ResponseResult selectUserListByCompetitionIdAndStatus(Integer competitionId, Integer status);

    ResponseResult updateStatusById(UserCompetition userCompetition);

    ResponseResult insertOne(UserCompetition userCompetition);

    ResponseResult selectOne(UserCompetition userCompetition);

}
