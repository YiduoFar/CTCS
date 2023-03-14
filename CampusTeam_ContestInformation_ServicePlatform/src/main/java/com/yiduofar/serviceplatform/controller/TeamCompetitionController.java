package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.domain.TeamCompetition;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.yiduofar.serviceplatform.service.TeamCompetitionService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.TeamCompetitionDeleteVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TeamCompetition)表控制层
 *
 * @author makejava
 * @since 2023-02-20 20:40:29
 */
@RestController
public class TeamCompetitionController extends ApiController {


    /**
     * 服务对象
     */
    @Resource
    private TeamCompetitionService teamCompetitionService;

    /**
     * 根据竞赛id查询竞赛的 参与者 或 申请者列表（组队）
     * @param competitionId
     * @param status
     * @return
     */
    @GetMapping("/team_competition/selectTeamListByCompetitionIdAndStatus")
    public ResponseResult selectTeamListByCompetitionIdAndStatus(@RequestParam("competitionId") Integer competitionId,
                                                                 @RequestParam("status") Integer status) {
        return teamCompetitionService.selectTeamListByCompetitionIdAndStatus(competitionId, status);
    }

    /**
     * 删除队伍竞赛关系 （教师剔除竞赛队伍）
     * @param teamCompetitionDeleteVo
     * @return
     */
    @PostMapping("/team_competition/deleteOne")
    public ResponseResult deleteOne(TeamCompetitionDeleteVo teamCompetitionDeleteVo) {
        return teamCompetitionService.deleteTeamCompetition(teamCompetitionDeleteVo);
    }

    /**
     * 更新队伍竞赛关系（同意、拒绝申请）
     * @param teamCompetition
     * @return
     */
    @PostMapping("/team_competition/updateStatusById")
    public ResponseResult updateStatusById(TeamCompetition teamCompetition) {
        return teamCompetitionService.updateStatusById(teamCompetition);
    }

    /**
     * 新增队伍竞赛关系（申请）
     * @param teamCompetition
     * @return
     */
    @PostMapping("/team_competition/insertOne")
    public ResponseResult insertOne(TeamCompetition teamCompetition) {
        return teamCompetitionService.insertOne(teamCompetition);
    }

    /**
     * 根据队长id和竞赛id查询队伍竞赛关系
     * @param userId
     * @param competitionId
     * @return
     */
    @GetMapping("/team_competition/selectByCaptionIdAndCompetitionId")
    public ResponseResult selectByCaptionIdAndCompetitionId(@RequestParam("userId") Integer userId,
                                                            @RequestParam("competitionId") Integer competitionId) {
        return teamCompetitionService.selectByCaptionIdAndCompetitionId(userId, competitionId);
    }
}

