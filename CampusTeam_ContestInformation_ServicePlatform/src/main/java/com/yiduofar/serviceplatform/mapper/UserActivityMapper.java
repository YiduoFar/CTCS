package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.domain.UserActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【user_activity】的数据库操作Mapper
* @createDate 2023-02-27 00:27:36
* @Entity com.yiduofar.serviceplatform.domain.UserActivity
*/
public interface UserActivityMapper extends BaseMapper<UserActivity> {

    List<User> selectUserListByActivityIdAndStatus(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    int updateStatusById(UserActivity userActivity);

    List<Activity> selectActivityListByUserIdAndStatus(@Param("userId") Integer userId,
                                                       @Param("status") Integer status);
}




