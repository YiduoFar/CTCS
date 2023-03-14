package com.yiduofar.serviceplatform.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/22 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamCompetitionDeleteVo {

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
    private Integer competitionId;

    /**
     *
     */
    private Integer status;
}
