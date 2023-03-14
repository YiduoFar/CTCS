package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/5 9:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDetailVo {
    /**
     * 队长
     */
    User captain;

    /**
     * 成员
     */
    List<User> memberList;

    /**
     * 队伍
     */
    Team team;

    /**
     * 竞赛
     */
    List<Competition> competitionList;

    /**
     * 活动
     */
    List<Activity> activityList;
}
