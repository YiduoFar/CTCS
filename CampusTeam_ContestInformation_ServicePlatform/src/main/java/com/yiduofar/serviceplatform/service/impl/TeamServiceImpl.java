package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.*;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.*;
import com.yiduofar.serviceplatform.service.TeamService;
import com.yiduofar.serviceplatform.util.UUIDUtil;
import com.yiduofar.serviceplatform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author HuiBBao
 * @description 针对表【team】的数据库操作Service实现
 * @createDate 2023-02-20 20:47:28
 */
@Service
@Slf4j
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserTeamMapper userTeamMapper;

    @Autowired
    TeamCompetitionMapper teamCompetitionMapper;

    @Autowired
    TeamActivityMapper teamActivityMapper;

    @Autowired
    UUIDUtil uuidUtil;

    /**
     * 根据队长id搜索队伍
     *
     * @param teamCaptionId
     * @return
     */
    @Override
    public ResponseResult selectTeamByCaptionId(Integer teamCaptionId) {
        List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>().eq("team_caption_id", teamCaptionId));
        if (CollectionUtils.isEmpty(teams)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(teams);
    }

    /**
     * 根据队长id搜索队伍Vo
     *
     * @param teamCaptionId
     * @return
     */
    @Override
    public ResponseResult selectTeamMemVoByCaptionId(Integer teamCaptionId) {
        // 初始化list
        List<TeamMemVo> myJoinedTeam = new ArrayList<>();
        List<TeamMemVo2> myCreatedTeam = new ArrayList<>();
        // 查询我加入的队伍
        List<Team> teams = userTeamMapper.selectTeamListByUserId(new QueryWrapper<UserTeam>().eq("ut.user_id", teamCaptionId).eq("ut.status", 1));
        if (!CollectionUtils.isEmpty(teams)) {
            for (Team team : teams) {
                // 队长
                User caption = userMapper.selectById(team.getTeamCaptionId());
                // 队员
                List<User> members = userTeamMapper.selectUserListByTeamId(new QueryWrapper<UserTeam>().eq("ut.team_id", team.getTeamId()).eq("ut.status", 1));
                // 装填
                TeamMemVo teamMemVo = new TeamMemVo(caption, members, team);
                myJoinedTeam.add(teamMemVo);
            }
        }
        // 查询我创建的队伍
        List<Team> teams2 = teamMapper.selectList(new QueryWrapper<Team>().eq("team_caption_id", teamCaptionId));
        if (!CollectionUtils.isEmpty(teams2)) {
            for (Team team : teams2) {
                // 队长
                User caption = userMapper.selectById(team.getTeamCaptionId());
                // 队员
                List<User> members = userTeamMapper.selectUserListByTeamId(new QueryWrapper<UserTeam>().eq("ut.team_id", team.getTeamId()).eq("ut.status", 1));
                // 申请者数量
                Integer count = userTeamMapper.selectCount(new QueryWrapper<UserTeam>().eq("team_id", team.getTeamId()).eq("status", 0));
                // 装填
                TeamMemVo2 teamMemVo2 = new TeamMemVo2(caption, members, team, count);
                myCreatedTeam.add(teamMemVo2);
            }
        }
        // 组装并返回
        return ResponseResult.success(new TeamJoinTeamCreate(myJoinedTeam, myCreatedTeam));
    }

    /**
     * 新增队伍
     *
     * @param team
     * @return
     */
    @Override
    public ResponseResult insertTeam(Team team) {
        // 如果用户未实名，不能进行创建
        User user = userMapper.selectById(team.getTeamCaptionId());
        if (!StringUtils.hasText(user.getRealName())) {
            throw new GlobalException(ResponseResultEnum.TEAM_CREATE_ERROR);
        } else {
            team.setTeamCaptionUserName(user.getUserName());
            team.setTeamCaptionRealName(user.getRealName());
        }
        // 如果该用户创建队伍数量超过5个，不允许再创建
        Integer countCreatedNum = teamMapper.selectCount(new QueryWrapper<Team>().eq("team_caption_id", team.getTeamCaptionId()));
        if (countCreatedNum >= 5) {
            throw new GlobalException(ResponseResultEnum.TEAM_CREATE_NUM_ERROR);
        }
        // 查看名字是否重复
        List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>().eq("team_name", team.getTeamName()));
        if (!CollectionUtils.isEmpty(teams)) {
            throw new GlobalException(ResponseResultEnum.TEAM_NAME_ERROR);
        }
        // 创建日期
        team.setTeamCreateTime(new Date());
        // 生成6位邀请码
        team.setTeamInviteCode(uuidUtil.createInviteCode());

        int insert = teamMapper.insert(team);
        if (insert != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success(team);
    }

    /**
     * 根据id搜索队伍
     *
     * @param teamId
     * @return
     */
    @Override
    public ResponseResult selectTeamById(Integer teamId) {
        Team team = teamMapper.selectById(teamId);
        if (Objects.isNull(team)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(team);
    }

    /**
     * 编辑队伍信息
     *
     * @param team
     * @return
     */
    @Override
    public ResponseResult updateTeamById(Team team) {
        if (team.getTeamMemberNumMax() < team.getTeamMemberNum()) {
            throw new GlobalException(ResponseResultEnum.TEAM_MEM_NUM_OVERSIZE_ERROR);
        }
        int update = teamMapper.updateById(team);
        if (update != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 查询队伍详情Vo
     *
     * @param teamId
     * @return
     */
    @Override
    public ResponseResult selectTeamDetailVoById(Integer teamId) {
        Team team = teamMapper.selectById(teamId);
        // 队长
        User user = userMapper.selectById(team.getTeamCaptionId());
        // 队员
        List<User> users = userTeamMapper.selectUserListByTeamId(new QueryWrapper<UserTeam>().eq("ut.team_id", teamId).eq("ut.status", 1));
        // 竞赛
        List<Competition> competitionList = teamCompetitionMapper.selectCompetitionByTeamIdAndStatus(teamId);
        // 活动
        List<Activity> activityList = teamActivityMapper.selectActivityByTeamIdAndStatus(teamId);
        // Vo
        TeamDetailVo teamDetailVo = new TeamDetailVo(user, users, team, competitionList, activityList);
        return ResponseResult.success(teamDetailVo);
    }

    /**
     * 队伍招募列表
     *
     * @return
     */
    @Override
    public ResponseResult selectTeamRecruit() {
        // 队伍招募
        List<Team> teams = teamMapper.selectList(new QueryWrapper<Team>().eq("team_status", 1).orderByDesc("team_create_time"));
        return ResponseResult.success(teams);
    }

    /**
     * 根据用户id查询最近加入的队伍
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult selectTeamRencentlyJoinedByUserId(Integer userId) {
        List<Team> teams = userTeamMapper.selectTeamListByUserIdOrderByTeamId(new QueryWrapper<UserTeam>()
                .eq("ut.user_id", userId).eq("ut.status", 1));
        if (CollectionUtils.isEmpty(teams)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 前3
        teams = teams.subList(0, Math.min(teams.size(), 3));
        return ResponseResult.success(teams);
    }

    /**
     * 查询所有队伍
     *
     * @return
     */
    @Override
    public ResponseResult selectTeamByKeyword(QueryVo queryVo) {
        // 封装分页对象
        Page<Team> page = new Page<>(queryVo.getCurrent(), queryVo.getSize());
        // 根据关键字模糊查询
        List<Team> records = teamMapper.selectPage2(page, new QueryWrapper<>()
                .like("team_name", queryVo.getKeyword()));
        if (CollectionUtils.isEmpty(records)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        // 封装Vo，带上发布者信息
        List<TeamMemVo3> pageList = new ArrayList<>();
        for (Team team : records) {
            TeamMemVo3 teamMemVo3 = new TeamMemVo3();
            teamMemVo3.setTeam(team);
            teamMemVo3.setCaption(userMapper.selectById(team.getTeamCaptionId()));
            pageList.add(teamMemVo3);
        }
        // 总数
        Long total = page.getTotal();
        return ResponseResult.success(new TeamCaptainPageTotal(pageList, total));
    }

    /**
     * 查询队伍星级排行
     *
     * @return
     */
    @Override
    public ResponseResult selectTeamStar() {
        List<TeamStarVo> list = teamMapper.selectTeamStar();
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(ResponseResultEnum.NULL_DATA_ERROR);
        }
        return ResponseResult.success(list);
    }


    /**
     * 解散队伍
     *
     * @param team
     * @return
     */
    @Override
    public ResponseResult deleteTeamById(Team team) {
        int delete = teamMapper.deleteById(team.getTeamId());
        if (delete != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

}




