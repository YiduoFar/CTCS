package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【user_competition】的数据库操作Mapper
* @createDate 2023-02-14 15:30:09
* @Entity com.yiduofar.serviceplatform.domain.UserCompetition
*/
public interface UserCompetitionMapper extends BaseMapper<UserCompetition> {

    List<User> selectUserListByCompetitionIdAndStatus(@Param(Constants.WRAPPER) QueryWrapper<UserCompetition> queryWrapper);

    int updateStatusById(UserCompetition userCompetition);

    List<Competition> selectCompetitionListByUserIdAndStatus(@Param("userId") Integer userId,
                                                             @Param("status") Integer status);
}




