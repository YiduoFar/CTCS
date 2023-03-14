package com.yiduofar.serviceplatform.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/4 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTeamDeleteVo {
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
     * 队伍id
     */
    private Integer teamId;

    /**
     * 状态（0未确认，1确认）
     */
    private Integer status;
}
