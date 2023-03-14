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
 * @create 2023/3/5 17:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamMemVo3 {
    /**
     * 队长
     */
    User caption;

    /**
     * 队伍
     */
    Team team;
}
