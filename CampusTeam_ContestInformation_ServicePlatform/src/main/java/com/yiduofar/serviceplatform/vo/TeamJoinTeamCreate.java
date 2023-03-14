package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/2 20:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamJoinTeamCreate {
    List<TeamMemVo> myJoinedTeam;
    List<TeamMemVo2> myCreatedTeam;
}
