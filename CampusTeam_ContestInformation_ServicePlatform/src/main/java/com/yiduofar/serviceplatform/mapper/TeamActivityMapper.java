package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.TeamActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.domain.TeamCompetition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【team_activity】的数据库操作Mapper
* @createDate 2023-02-27 00:28:02
* @Entity com.yiduofar.serviceplatform.domain.TeamActivity
*/
public interface TeamActivityMapper extends BaseMapper<TeamActivity> {

    List<TeamActivity> selectTeamActivityList(@Param("userId") Integer userId, @Param("status") int status);

    List<Team> selectTeamListByActivityIdAndStatus(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    TeamActivity selectByTeamIdsAndActivityId(@Param("teamIds")List<Integer> teamIds,@Param("activityId") Integer activityId);

    int updateStatusById(TeamActivity teamActivity);

    List<Activity> selectActivityByTeamId(@Param("teamId")Integer teamId);

    List<Activity> selectActivityByTeamIdAndStatus(@Param("teamId")Integer teamId);
}




