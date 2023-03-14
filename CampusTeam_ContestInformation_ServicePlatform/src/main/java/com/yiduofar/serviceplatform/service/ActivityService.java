package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;

/**
* @author HuiBBao
* @description 针对表【activity】的数据库操作Service
* @createDate 2023-02-27 00:18:57
*/
public interface ActivityService extends IService<Activity> {

    ResponseResult selectMyActivityByKeyWordAndStatus(QueryVo queryVo);

    ResponseResult selectActivityByInitiatorId(QueryVo queryVo);

    ResponseResult deleteActivityById(Integer activityId);

    ResponseResult insertActivity(Activity activity);

    ResponseResult selectActivityByKeyword(QueryVo queryVo);

    ResponseResult selectLatestActivity();

    ResponseResult selectActivityDetailVoById(Integer activityId);

    ResponseResult selectActivityById(Integer activityId);

    ResponseResult updateActivityById(Activity activity);
}
