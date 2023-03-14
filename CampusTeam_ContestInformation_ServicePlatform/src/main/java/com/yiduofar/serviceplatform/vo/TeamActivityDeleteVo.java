package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/27 1:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamActivityDeleteVo {
    /**
     * type 操作类型 剔除3 婉拒2 退出1
     */
    private Integer type;

    /**
     *
     */
    private Integer teamId;

    /**
     *
     */
    private Integer activityId;

    /**
     *
     */
    private Integer status;
}
