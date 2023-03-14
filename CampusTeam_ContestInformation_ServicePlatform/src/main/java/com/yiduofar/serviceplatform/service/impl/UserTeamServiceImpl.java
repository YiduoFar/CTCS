package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.domain.UserTeam;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.TeamMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.service.UserTeamService;
import com.yiduofar.serviceplatform.mapper.UserTeamMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.UserTeamDeleteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageTip.USER_TEAM_DELETE_TIP;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_BACKSTAGE;
import static com.yiduofar.serviceplatform.constant.SystemConstant.MessageUrl.TO_TEAM;

/**
 * @author HuiBBao
 * @description 针对表【user_team】的数据库操作Service实现
 * @createDate 2023-03-02 20:52:25
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
        implements UserTeamService {

    @Autowired
    UserTeamMapper userTeamMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    MessageService messageService;

    /**
     * 删除用户队伍关系
     *
     * @param userTeamDeleteVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteOne(UserTeamDeleteVo userTeamDeleteVo) {
        int delete = userTeamMapper.delete(new QueryWrapper<UserTeam>()
                .eq("user_id", userTeamDeleteVo.getUserId())
                .eq("team_id", userTeamDeleteVo.getTeamId())
                .eq("status", userTeamDeleteVo.getStatus()));
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        Team team = teamMapper.selectById(userTeamDeleteVo.getTeamId());
        String content = USER_TEAM_DELETE_TIP[userTeamDeleteVo.getType() - 1] + team.getTeamName();
        messageService.sendMessageToUser(userTeamDeleteVo.getUserId(), content, TO_BACKSTAGE + 7, 1);
        return ResponseResult.success();
    }

    /**
     * 根据队伍id和状态查询参与者或申请者列表
     *
     * @param teamId
     * @param status
     * @return
     */
    @Override
    public ResponseResult selectUserListByTeamIdAndStatus(Integer teamId, Integer status) {
        List<User> users = userTeamMapper.selectUserListByTeamId(new QueryWrapper<UserTeam>()
                .eq("ut.team_id", teamId)
                .eq("ut.status", status));
        if (CollectionUtils.isEmpty(users)) {
            throw new GlobalException(ResponseResultEnum.NULL_USER_LIST_ERROR);
        }
        return ResponseResult.success(users);
    }

    /**
     * 同意申请
     *
     * @param userTeam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateStatusById(UserTeam userTeam) {
        if (userTeam.getStatus() != 1) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        Team team = teamMapper.selectById(userTeam.getTeamId());
        // 增加队伍人员数量
        UpdateWrapper<Team> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("team_member_num = team_member_num + 1")
                .eq("team_id", team.getTeamId());
        int update = teamMapper.update(team, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 更新数据库
        int count = userTeamMapper.updateStatusById(userTeam);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 给用户发送消息
        String content = "成功加入队伍: " + team.getTeamName();
        messageService.sendMessageToUser(userTeam.getUserId(), content, TO_TEAM + team.getTeamId(), 0);
        return ResponseResult.success();
    }


    /**
     * 申请
     *
     * @param userTeam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertOne(UserTeam userTeam) {
        if (userTeam.getStatus() != 0) {
            throw new GlobalException(ResponseResultEnum.ERROR);
        }
        int insert = userTeamMapper.insert(userTeam);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        User user = userMapper.selectById(userTeam.getUserId());
        Team team = teamMapper.selectById(userTeam.getTeamId());
        String content = "提交了队伍申请：" + team.getTeamName();
        String content2 = user.getUserName() + "申请加入：" + team.getTeamName();
        messageService.sendMessageToUser(userTeam.getUserId(), content, TO_TEAM + team.getTeamId(), 0);
        messageService.sendMessageToTeam(userTeam.getTeamId(), content2, TO_BACKSTAGE + 7, 0);
        return ResponseResult.success();
    }

    /**
     * 检查是否存在关系
     *
     * @param userId
     * @param teamId
     * @return
     */
    @Override
    public ResponseResult selectAndJudgeOne(Integer userId, Integer teamId) {
        Team team = teamMapper.selectById(teamId);
        if (userId == team.getTeamCaptionId()) {
            throw new GlobalException(ResponseResultEnum.TEAM_JOIN_ERROR3);
        }
        List<UserTeam> userTeams = userTeamMapper.selectList(new QueryWrapper<UserTeam>()
                .eq("user_id", userId)
                .eq("team_id", teamId));
        // 数据不止一条
        if (userTeams.size() > 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        if (userTeams.size() == 1) {
            // 已经申请加入
            if (userTeams.get(0).getStatus() == 0) {
                throw new GlobalException(ResponseResultEnum.TEAM_JOIN_ERROR);
            }
            // 已经加入
            if (userTeams.get(0).getStatus() == 1) {
                throw new GlobalException(ResponseResultEnum.TEAM_JOIN_ERROR2);
            }
        }
        return ResponseResult.success();
    }

    /**
     * 邀请码入队
     * @param userId
     * @param teamInviteCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult fastJoinTeam(Integer userId, Integer teamInviteCode) {
        // 根据邀请码查出队伍
        List<Team> teams = teamMapper.selectList(new LambdaQueryWrapper<Team>()
                .eq(Team::getTeamInviteCode, teamInviteCode));
        if (CollectionUtils.isEmpty(teams) || teams.size() != 1) {
            throw new GlobalException(ResponseResultEnum.TEAM_NULL_ERROR);
        }
        Team team = teams.get(0);
        UserTeam userTeam = new UserTeam(userId, team.getTeamId(), 1);
        // 检查
        selectAndJudgeOne(userId, team.getTeamId());
        // 插入新记录
        int insert = userTeamMapper.insert(userTeam);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 增加队伍人员数量
        UpdateWrapper<Team> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("team_member_num = team_member_num + 1")
                .eq("team_id", team.getTeamId());
        int update = teamMapper.update(team, updateWrapper);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        // 发消息
        User user = userMapper.selectById(userId);
        String content = "成功加入队伍: " + team.getTeamName();
        String content2 = user.getUserName() + "通过邀请码加入了队伍: " + team.getTeamName();
        messageService.sendMessageToUser(userId, content, TO_BACKSTAGE + 7, 0);
        messageService.sendMessageToUser(team.getTeamCaptionId(), content2, TO_BACKSTAGE + 7, 1);
        return ResponseResult.success(team);
    }

}




