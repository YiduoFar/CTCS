package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.UserActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserActivityDeleteVo;

/**
* @author HuiBBao
* @description 针对表【user_activity】的数据库操作Service
* @createDate 2023-02-27 00:27:36
*/
public interface UserActivityService extends IService<UserActivity> {

    ResponseResult deleteUserActivity(UserActivityDeleteVo userActivityDeleteVo);

    ResponseResult selectOne(UserActivity userActivity);

    ResponseResult insertOne(UserActivity userActivity);

    ResponseResult selectUserListByActivityIdAndStatus(Integer activityId, Integer status);

    ResponseResult updateStatusById(UserActivity userActivity);

}
