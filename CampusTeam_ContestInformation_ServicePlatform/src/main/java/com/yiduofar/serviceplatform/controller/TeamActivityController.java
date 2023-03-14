package com.yiduofar.serviceplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.TeamActivity;
import com.yiduofar.serviceplatform.domain.TeamCompetition;
import com.yiduofar.serviceplatform.service.TeamActivityService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.TeamActivityDeleteVo;
import com.yiduofar.serviceplatform.vo.TeamCompetitionDeleteVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (TeamActivity)表控制层
 *
 * @author makejava
 * @since 2023-02-27 00:28:47
 */
@RestController
public class TeamActivityController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TeamActivityService teamActivityService;

    /**
     * 删除队伍活动关系 （教师剔除竞赛队伍）
     *
     * @param teamActivityDeleteVo
     * @return
     */
    @PostMapping("/team_activity/deleteOne")
    public ResponseResult deleteOne(TeamActivityDeleteVo teamActivityDeleteVo) {
        return teamActivityService.deleteTeamActivity(teamActivityDeleteVo);
    }

    /**
     * 根据队长id和活动id查询队伍活动关系
     *
     * @param userId
     * @param activityId
     * @return
     */
    @GetMapping("/team_activity/selectByCaptionIdAndActivityId")
    public ResponseResult selectByCaptionIdAndActivityId(@RequestParam("userId") Integer userId,
                                                         @RequestParam("activityId") Integer activityId) {
        return teamActivityService.selectByCaptionIdAndActivityId(userId, activityId);
    }

    /**
     * 根据活动id查询活动的 参与者 或 申请者列表（组队）
     * @param activityId
     * @param status
     * @return
     */
    @GetMapping("/team_activity/selectTeamListByActivityIdAndStatus")
    public ResponseResult selectTeamListByActivityIdAndStatus(@RequestParam("activityId") Integer activityId,
                                                                 @RequestParam("status") Integer status) {
        return teamActivityService.selectTeamListByActivityIdAndStatus(activityId, status);
    }


    /**
     * 新增队伍活动关系（申请）
     *
     * @param teamActivity
     * @return
     */
    @PostMapping("/team_activity/insertOne")
    public ResponseResult insertOne(TeamActivity teamActivity) {
        return teamActivityService.insertOne(teamActivity);
    }

    /**
     * 更新队伍竞赛关系（同意、拒绝申请）
     * @param teamActivity
     * @return
     */
    @PostMapping("/team_activity/updateStatusById")
    public ResponseResult updateStatusById(TeamActivity teamActivity) {
        return teamActivityService.updateStatusById(teamActivity);
    }

}

