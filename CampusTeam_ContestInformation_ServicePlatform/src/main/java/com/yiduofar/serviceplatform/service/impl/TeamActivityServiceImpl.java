package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.TeamActivityService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.TeamActivityDeleteVo;
import com.yiduofar.serviceplatform.vo.TeamCompetitionDeleteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.TEAM_ACTIVITY_DELETE_TIP;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.USER_ACTIVITY_DELETE_TIP;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_ACTIVITY;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_TEAM;

/**
 * @author HuiBBao
 * @description 针对表【team_activity】的数据库操作Service实现
 * @createDate 2023-02-27 00:28:02
 */
@Service
public class TeamActivityServiceImpl extends ServiceImpl<TeamActivityMapper, TeamActivity>
        implements TeamActivityService {

    @Autowired
    TeamActivityMapper teamActivityMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    TeamMapper teamMapper;

    @Override
    public ResponseResult deleteTeamActivity(TeamActivityDeleteVo teamActivityDeleteVo) {
        /**
         * 删除队伍活动关系 （教师剔除活动队伍）
         * @param teamCompetitionDeleteVo
         * @return
         */
        int delete = teamActivityMapper.delete(new QueryWrapper<TeamActivity>()
                .eq("team_id", teamActivityDeleteVo.getTeamId())
                .eq("activity_id", teamActivityDeleteVo.getActivityId())
                .eq("status", teamActivityDeleteVo.getStatus()));
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        Activity activity = activityMapper.selectById(teamActivityDeleteVo.getActivityId());
        String content = TEAM_ACTIVITY_DELETE_TIP[teamActivityDeleteVo.getType() - 1] + activity.getActivityName();
        messageService.sendMessageToTeam(teamActivityDeleteVo.getTeamId(), content, TO_ACTIVITY + activity.getActivityId(), 0);
        return ResponseResult.success();

    }

    /**
     * 根据队长id和活动id查询队伍活动关系
     *
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public ResponseResult selectByCaptionIdAndActivityId(Integer userId, Integer activityId) {
        List<Integer> teamIds = teamMapper.selectIdsByCaptionId(userId);
        if (CollectionUtils.isEmpty(teamIds)) {
            return ResponseResult.success();
        }
        TeamActivity teamActivity = teamActivityMapper.selectByTeamIdsAndActivityId(teamIds, activityId);
        return ResponseResult.success(teamActivity);
    }

    /**
     * 新增队伍活动关系（申请）
     *
     * @param teamActivity
     * @return
     */
    @Override
    public ResponseResult insertOne(TeamActivity teamActivity) {
        Team team = teamMapper.selectById(teamActivity.getTeamId());
        Activity activity = activityMapper.selectById(teamActivity.getActivityId());
        // 检查要求
        // 人数
        if (team.getTeamMemberNum() < activity.getSignUpMinTeamMemberNum() ||
                team.getTeamMemberNum() > activity.getSignUpMaxTeamMemberNum()) {
            throw new GlobalException(ResponseResultEnum.TEAM_MEM_NUM_ERROR);
        }
        // 受众
        if (activity.getActivityAudience() == 1) {
            if (!activity.getActivityAudienceName().equals(team.getTeamCollege())) {
                throw new GlobalException(ResponseResultEnum.TEAM_AUDIENCE_ERROR);
            }
        }
        if (activity.getActivityAudience() == 2) {
            if (!activity.getActivityAudienceName().equals(team.getTeamSpecialty())) {
                throw new GlobalException(ResponseResultEnum.TEAM_SPECIALTY_ERROR);
            }
        }
        // 申请
        int count = teamActivityMapper.insert(teamActivity);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        if (!Objects.isNull(team) && !Objects.isNull(activity)) {
            // 给队伍（队长）发消息 通知 type 0
            String content = "提交了待处理的申请: " + activity.getActivityName();
            messageService.sendMessageToTeam(team.getTeamId(), content, TO_ACTIVITY + activity.getActivityId(), 0);
            // 给发布人发消息 申请 type 1
            String content2 = team.getTeamName() + " 申请参加" + activity.getActivityName();
            messageService.sendMessageToUser(activity.getActivityInitiatorId(), content2, TO_ACTIVITY + activity.getActivityId(), 1);
        }
        return ResponseResult.success();
    }

    /**
     * 根据活动id查询活动的 参与者 或 申请者列表（组队）
     * @param activityId
     * @param status
     * @return
     */
    @Override
    public ResponseResult selectTeamListByActivityIdAndStatus(Integer activityId, Integer status) {
        List<Team> teams = teamActivityMapper.selectTeamListByActivityIdAndStatus(new QueryWrapper<>()
                .eq("ta.activity_id", activityId)
                .eq("ta.status", status));
        if (CollectionUtils.isEmpty(teams)) {
            throw new GlobalException(ResponseResultEnum.NULL_TEAM_LIST_ERROR);
        }
        return ResponseResult.success(teams);
    }

    /**
     * 更新队伍竞赛关系（同意申请）
     * @param teamActivity
     * @return
     */
    @Override
    public ResponseResult updateStatusById(TeamActivity teamActivity) {
        if (teamActivity.getStatus() != 1) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        int count = teamActivityMapper.updateStatusById(teamActivity);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 给队伍发送消息
        Activity activity = activityMapper.selectById(teamActivity.getActivityId());
        String content = "申请通过: " + activity.getActivityName();
        messageService.sendMessageToTeam(teamActivity.getTeamId(), content, TO_ACTIVITY + teamActivity.getActivityId(), 0);
        return ResponseResult.success();
    }
}




