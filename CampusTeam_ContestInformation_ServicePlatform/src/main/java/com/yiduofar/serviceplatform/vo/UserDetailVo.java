package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/9 16:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailVo {

    /**
     * 用户
     */
    User user;

    /**
     * 加入的队伍
     */
    List<Team> teamList;

    /**
     * 加入的竞赛
     */
    List<Competition> competitionList;

    /**
     * 加入的活动
     */
    List<Activity> activityList;

    /**
     * 他的帖子
     */
    List<Article> articleList;

}
