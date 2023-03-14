package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.UserTeam;
import com.yiduofar.serviceplatform.service.UserTeamService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserTeamDeleteVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserTeam)表控制层
 *
 * @author makejava
 * @since 2023-03-02 20:52:40
 */
@RestController
public class UserTeamController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserTeamService userTeamService;

    /**
     * 删除用户队伍关系
     * @param userTeamDeleteVo
     * @return
     */
    @PostMapping("/user_team/deleteOne")
    public ResponseResult deleteOne(UserTeamDeleteVo userTeamDeleteVo) {
        return userTeamService.deleteOne(userTeamDeleteVo);
    }

    /**
     * 根据队伍id和状态查询参与者或申请者列表
     * @param teamId
     * @param status
     * @return
     */
    @GetMapping("/user_team/selectUserListByTeamIdAndStatus")
    public ResponseResult selectUserListByTeamIdAndStatus(@RequestParam(value = "teamId") Integer teamId,
                                                          @RequestParam(value = "status") Integer status) {
        return userTeamService.selectUserListByTeamIdAndStatus(teamId, status);
    }

    /**
     * 同意申请
     * @param userTeam
     * @return
     */
    @PostMapping("/user_team/updateStatusById")
    public ResponseResult updateStatusById(UserTeam userTeam) {
        return userTeamService.updateStatusById(userTeam);
    }

    /**
     * 申请
     * @param userTeam
     * @return
     */
    @PostMapping("/user_team/insertOne")
    public ResponseResult insertOne(UserTeam userTeam) {
        return userTeamService.insertOne(userTeam);
    }

    /**
     * 邀请码入队
     * @param userId
     * @param teamInviteCode
     * @return
     */
    @PostMapping("/user_team/fastJoinTeam")
    public ResponseResult fastJoinTeam(@RequestParam(value = "userId") Integer userId,
                                       @RequestParam(value = "teamInviteCode")Integer teamInviteCode) {
        return userTeamService.fastJoinTeam(userId, teamInviteCode);
    }

    /**
     * 检查是否存在关系
     * @param userId
     * @param teamId
     * @return
     */
    @GetMapping("/user_team/selectAndJudgeOne")
    public ResponseResult selectAndJudgeOne(@RequestParam(value = "userId") Integer userId,
                                    @RequestParam(value = "teamId") Integer teamId) {
        return userTeamService.selectAndJudgeOne(userId, teamId);
    }

}

