package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.UserActivity;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.yiduofar.serviceplatform.service.UserActivityService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserActivityDeleteVo;
import com.yiduofar.serviceplatform.vo.UserCompetitionDeleteVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserActivity)表控制层
 *
 * @author makejava
 * @since 2023-02-27 00:28:29
 */
@RestController
public class UserActivityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserActivityService userActivityService;

    /**
     * 根据活动id、状态查询活动的 参与者 或 申请者列表（个人）
     * @param activityId
     * @param status
     * @return
     */
    @GetMapping("/user_activity/selectUserListByActivityIdAndStatus")
    public ResponseResult selectUserListByActivityIdAndStatus(@RequestParam("activityId") Integer activityId,
                                                                 @RequestParam("status") Integer status) {
        return userActivityService.selectUserListByActivityIdAndStatus(activityId, status);
    }

    /**
     * 删除一条用户活动关系
     * userActivityDeleteVo.type为操作类型 剔除3 婉拒2 退出1
     * @param userActivityDeleteVo
     * @return
     */
    @PostMapping("/user_activity/deleteOne")
    public ResponseResult deleteOne(UserActivityDeleteVo userActivityDeleteVo) {
        return userActivityService.deleteUserActivity(userActivityDeleteVo);
    }

    /**
     * 查询一条用户活动关系
     * @param userActivity
     * @return
     */
    @PostMapping("/user_activity/selectOne")
    public ResponseResult selectOne(UserActivity userActivity) {
        return userActivityService.selectOne(userActivity);
    }

    /**
     * 新增用户活动关系（申请）
     * @param userActivity
     * @return
     */
    @PostMapping("/user_activity/insertOne")
    public ResponseResult insertOne(UserActivity userActivity) {
        return userActivityService.insertOne(userActivity);
    }

    /**
     * 更新用户活动关系（同意申请）
     * @param userActivity
     * @return
     */
    @PostMapping("/user_activity/updateStatusById")
    public ResponseResult updateStatusById(UserActivity userActivity) {
        return userActivityService.updateStatusById(userActivity);
    }
}

