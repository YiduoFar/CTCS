package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/24 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPointVo {

    /**
     * 头像
     */
    private String userHead;

    /**
     * 名字
     */
    private String userName;

    /**
     * 分数
     */
    private Integer point;

}
