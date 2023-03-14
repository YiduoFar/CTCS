package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/6 10:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamStarVo {
    /**
     * 队伍名
     */
    String teamName;
    /**
     * 星级
     */
    Double teamStar;
}
