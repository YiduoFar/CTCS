package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.domain.UserTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【user_team】的数据库操作Mapper
* @createDate 2023-03-02 20:52:25
* @Entity com.yiduofar.serviceplatform.domain.UserTeam
*/
public interface UserTeamMapper extends BaseMapper<UserTeam> {

    List<User> selectUserListByTeamId(@Param(Constants.WRAPPER) QueryWrapper<UserTeam> eq);

    List<Team> selectTeamListByUserId(@Param(Constants.WRAPPER) QueryWrapper<UserTeam> eq);

    int updateStatusById(UserTeam userTeam);

    List<Team> selectTeamListByUserIdOrderByTeamId(@Param(Constants.WRAPPER) QueryWrapper<UserTeam> eq);
}




