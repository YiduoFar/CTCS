package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.service.TeamService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * (Team)表控制层
 *
 * @author makejava
 * @since 2023-02-23 00:25:20
 */
@RestController
public class TeamController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private TeamService teamService;

    /**
     * 根据队长id搜索队伍
     * @param teamCaptionId
     * @return
     */
    @GetMapping("/team/selectTeamByCaptionId")
    public ResponseResult selectTeamByCaptionId(Integer teamCaptionId) {
        return teamService.selectTeamByCaptionId(teamCaptionId);
    }

    /**
     * 根据队长id搜索队伍Vo
     * @param teamCaptionId
     * @return
     */
    @GetMapping("/team/selectTeamMemVoByCaptionId")
    public ResponseResult selectTeamMemVoByCaptionId(Integer teamCaptionId) {
        return teamService.selectTeamMemVoByCaptionId(teamCaptionId);
    }

    /**
     * 新增队伍
     * @param team
     * @return
     */
    @PostMapping("/team/insertTeam")
    public ResponseResult insertTeam(@Valid Team team) {
        return teamService.insertTeam(team);
    }

    /**
     * 根据id搜索队伍
     * @param teamId
     * @return
     */
    @GetMapping("/team/selectTeamById")
    public ResponseResult selectTeamById(Integer teamId) {
        return teamService.selectTeamById(teamId);
    }

    /**
     * 编辑队伍信息
     * @param team
     * @return
     */
    @PostMapping("/team/updateTeamById")
    public ResponseResult updateTeamById(Team team) {
        return teamService.updateTeamById(team);
    }

    /**
     * 解散队伍
     * @param team
     * @return
     */
    @PostMapping("/team/deleteTeamById")
    public ResponseResult deleteTeamById(Team team) {
        return teamService.deleteTeamById(team);
    }

    /**
     * 查询队伍详情Vo
     * @param teamId
     * @return
     */
    @GetMapping("/team/selectTeamDetailVoById")
    public ResponseResult selectTeamDetailVoById(Integer teamId) {
        return teamService.selectTeamDetailVoById(teamId);
    }

    /**
     * 队伍招募列表
     * @return
     */
    @GetMapping("/team/selectTeamRecruit")
    public ResponseResult selectTeamRecruit() {
        return teamService.selectTeamRecruit();
    }

    /**
     * 根据用户id查询最近加入的队伍
     * @param userId
     * @return
     */
    @GetMapping("/team/selectTeamRencentlyJoinedByUserId")
    public ResponseResult selectTeamRencentlyJoinedByUserId(Integer userId) {
        return teamService.selectTeamRencentlyJoinedByUserId(userId);
    }

    /**
     * 分页
     * 查询所有队伍
     * @return
     */
    @PostMapping("/team/selectTeamByKeyword")
    public ResponseResult selectTeamByKeyword(QueryVo queryVo) {
        return teamService.selectTeamByKeyword(queryVo);
    }

    /**
     * 查询队伍星级排行
     * @return
     */
    @GetMapping("/team/selectTeamStar")
    public ResponseResult selectTeamStar() {
        return teamService.selectTeamStar();
    }


}

