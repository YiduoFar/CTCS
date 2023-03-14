package com.yiduofar.serviceplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.service.ActivityService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * (Activity)表控制层
 *
 * @author makejava
 * @since 2023-02-27 00:19:14
 */
@RestController
public class ActivityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ActivityService activityService;

    /**
     * 查询ActivityDetailVo
     * @param activityId
     * @return
     */
    @PostMapping("/activity/selectActivityDetailVoById")
    public ResponseResult selectActivityDetailVoById(@RequestParam(value = "activityId") Integer activityId){
        return activityService.selectActivityDetailVoById(activityId);
    }

    /**
     * 分页
     * 根据关键字查询活动
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/show/activity/selectActivityByKeyword")
    public ResponseResult selectActivityByKeyword(QueryVo queryVo) {
        return activityService.selectActivityByKeyword(queryVo);
    }

    /**
     * 查询最新的5个活动
     *
     * @param
     * @return
     */
    @GetMapping("/show/activity/selectLatestActivity")
    public ResponseResult selectLatestActivity() {
        return activityService.selectLatestActivity();
    }

    /**
     * 分页
     * 根据用户id、关键字和状态查询活动
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/activity/selectMyActivityByKeyWordAndStatus")
    public ResponseResult selectMyActivityByKeyWordAndStatus(QueryVo queryVo) {
        return activityService.selectMyActivityByKeyWordAndStatus(queryVo);
    }

    /**
     * 分页
     * 根据发布者id查询活动
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/activity/selectActivityByInitiatorId")
    public ResponseResult selectActivityByInitiatorId(QueryVo queryVo) {
        return activityService.selectActivityByInitiatorId(queryVo);
    }

    /**
     * 根据id删除活动
     *
     * @param activityId
     * @return
     */
    @PostMapping("/activity/deleteActivityById")
    public ResponseResult deleteActivityById(@RequestParam(value = "activityId") Integer activityId) {
        return activityService.deleteActivityById(activityId);
    }


    /**
     * 新增活动
     *
     * @param activity
     * @return
     */
    @PostMapping("/activity/insertActivity")
    public ResponseResult insertActivity(@Valid Activity activity) {
        System.out.println(activity.toString());
        return activityService.insertActivity(activity);
    }

    /**
     * 根据id查询活动
     * @param activityId
     * @return
     */
    @GetMapping("/activity/selectActivityById")
    public ResponseResult selectActivityById(@RequestParam(value = "activityId") Integer activityId) {
        return activityService.selectActivityById(activityId);
    }

    /**
     * 编辑活动（更新）
     * @param activity
     * @return
     */
    @PostMapping("/activity/updateActivityById")
    public ResponseResult updateActivityById(Activity activity) {
        return activityService.updateActivityById(activity);
    }


}

