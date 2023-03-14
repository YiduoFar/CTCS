package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【team_competition】的数据库操作Mapper
* @createDate 2023-02-20 20:40:18
* @Entity com.yiduofar.serviceplatform.domain.TeamCompetition
*/
public interface TeamCompetitionMapper extends BaseMapper<TeamCompetition> {

    List<Team> selectTeamListByCompetitionIdAndStatus(@Param(Constants.WRAPPER) QueryWrapper<UserCompetition> queryWrapper);

    int updateStatusById(TeamCompetition teamCompetition);

    List<TeamCompetition> selectTeamCompetitionList(@Param("userId") Integer userId,@Param("status") int status);

    TeamCompetition selectByTeamIdsAndCompetitionId(@Param("teamIds")List<Integer> teamIds,@Param("competitionId") Integer competitionId);

    List<Competition> selectCompetitionByTeamId(@Param("teamId")Integer teamId);

    List<Competition> selectCompetitionByTeamIdAndStatus(@Param("teamId") Integer teamId);
}




