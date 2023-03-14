package com.yiduofar.serviceplatform.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/22 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCompetitionDeleteVo {

    /**
     * type 操作类型 剔除3 婉拒2 退出1
     */
    private Integer type;

    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 竞赛id
     */
    private Integer competitionId;

    /**
     * 状态（0未确认，1确认）
     */
    private Integer status;

}
