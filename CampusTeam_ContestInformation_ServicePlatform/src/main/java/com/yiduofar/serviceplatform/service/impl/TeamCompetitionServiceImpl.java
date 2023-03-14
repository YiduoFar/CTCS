package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.CompetitionMapper;
import com.yiduofar.serviceplatform.mapper.TeamMapper;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.TeamCompetitionService;
import com.yiduofar.serviceplatform.mapper.TeamCompetitionMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.TeamCompetitionDeleteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.TEAM_COMPETITION_DELETE_TIP;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_BACKSTAGE;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_COMPETITION;

/**
 * @author HuiBBao
 * @description 针对表【team_competition】的数据库操作Service实现
 * @createDate 2023-02-20 20:40:18
 */
@Service
public class TeamCompetitionServiceImpl extends ServiceImpl<TeamCompetitionMapper, TeamCompetition> implements TeamCompetitionService {

    @Autowired
    TeamCompetitionMapper teamCompetitionMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    TeamMapper teamMapper;

    /**
     * 删除队伍竞赛关系 （教师剔除竞赛队伍）
     * @param teamCompetitionDeleteVo
     * @return
     */
    @Override
    public ResponseResult deleteTeamCompetition(TeamCompetitionDeleteVo teamCompetitionDeleteVo) {
        QueryWrapper<TeamCompetition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamCompetitionDeleteVo.getTeamId()).eq("competition_id", teamCompetitionDeleteVo.getCompetitionId()).eq("status", teamCompetitionDeleteVo.getStatus());
        int delete = teamCompetitionMapper.delete(queryWrapper);
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        Competition competition = competitionMapper.selectById(teamCompetitionDeleteVo.getCompetitionId());
        String content = TEAM_COMPETITION_DELETE_TIP[teamCompetitionDeleteVo.getType() - 1];
        messageService.sendMessageToTeam(teamCompetitionDeleteVo.getTeamId(), content, TO_BACKSTAGE + 2, 0);
        return ResponseResult.success();
    }

    /**
     * 根据竞赛id查询竞赛的 参与者 或 申请者列表
     * @param competitionId
     * @param status
     * @return
     */
    @Override
    public ResponseResult selectTeamListByCompetitionIdAndStatus(Integer competitionId, Integer status) {
        QueryWrapper<UserCompetition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tc.competition_id", competitionId).eq("tc.status", status);
        List<Team> teams = teamCompetitionMapper.selectTeamListByCompetitionIdAndStatus(queryWrapper);
        if (CollectionUtils.isEmpty(teams)) {
            throw new GlobalException(ResponseResultEnum.NULL_TEAM_LIST_ERROR);
        }
        return ResponseResult.success(teams);
    }

    /**
     * 更新队伍竞赛关系（同意申请）
     * @param teamCompetition
     * @return
     */
    @Override
    public ResponseResult updateStatusById(TeamCompetition teamCompetition) {
        if (teamCompetition.getStatus() != 1) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        int count = teamCompetitionMapper.updateStatusById(teamCompetition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 给队伍发送消息
        Competition competition = competitionMapper.selectById(teamCompetition.getCompetitionId());
        String content = "申请通过: " + competition.getCompetitionName();
        messageService.sendMessageToTeam(teamCompetition.getTeamId(), content, TO_BACKSTAGE + 2, 0);
        return ResponseResult.success();
    }

    /**
     * 新增队伍竞赛关系（申请）
     * @param teamCompetition
     * @return
     */
    @Override
    public ResponseResult insertOne(TeamCompetition teamCompetition) {
        Team team = teamMapper.selectById(teamCompetition.getTeamId());
        Competition competition = competitionMapper.selectById(teamCompetition.getCompetitionId());
        // 检查要求
        // 人数
        if (team.getTeamMemberNum() < competition.getSignUpMinTeamMemberNum() ||
                team.getTeamMemberNum() > competition.getSignUpMaxTeamMemberNum()) {
            throw new GlobalException(ResponseResultEnum.TEAM_MEM_NUM_ERROR);
        }
        // 受众
        if (competition.getCompetitionAudience() == 1) {
            if (!competition.getCompetitionAudienceName().equals(team.getTeamCollege())) {
                throw new GlobalException(ResponseResultEnum.TEAM_AUDIENCE_ERROR);
            }
        }
        if (competition.getCompetitionAudience() == 2) {
            if (!competition.getCompetitionAudienceName().equals(team.getTeamSpecialty())) {
                throw new GlobalException(ResponseResultEnum.TEAM_SPECIALTY_ERROR);
            }
        }
        // 申请
        int count = teamCompetitionMapper.insert(teamCompetition);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        if (!Objects.isNull(team) && !Objects.isNull(competition)) {
            // 给队伍（队长）发消息 通知 type 0
            String content = "提交了待处理的申请: " + competition.getCompetitionName();
            messageService.sendMessageToTeam(team.getTeamId(), content, TO_COMPETITION + competition.getCompetitionId(), 0);
            // 给负责的老师发消息 申请 type 1
            String content2 = team.getTeamName() + " 申请参加" + competition.getCompetitionName();
            messageService.sendMessageToUser(competition.getCompetitionUndertaker3Id(), content2, TO_BACKSTAGE + 4, 1);
        }
        return ResponseResult.success();
    }

    /**
     * 根据队长id和竞赛id查询队伍竞赛关系
     * @param userId
     * @param competitionId
     * @return
     */
    @Override
    public ResponseResult selectByCaptionIdAndCompetitionId(Integer userId, Integer competitionId) {
        List<Integer> teamIds = teamMapper.selectIdsByCaptionId(userId);
        if (CollectionUtils.isEmpty(teamIds)) {
            return ResponseResult.success();
        }
        TeamCompetition teamCompetition = teamCompetitionMapper.selectByTeamIdsAndCompetitionId(teamIds, competitionId);
        return ResponseResult.success(teamCompetition);
    }

}




