package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/6 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExcellentTeamVo {
    /**
     * 队伍
     */
    Team team;
    /**
     * 参赛数
     */
    Integer num;
}
