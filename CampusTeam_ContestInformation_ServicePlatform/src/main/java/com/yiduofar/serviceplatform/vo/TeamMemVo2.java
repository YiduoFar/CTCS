package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/4 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamMemVo2 {
    /**
     * 队长
     */
    User caption;

    /**
     * 成员
     */
    List<User> memberList;

    /**
     * 队伍
     */
    Team team;

    /**
     * 申请人数
     */
    Integer applyNum;
}
