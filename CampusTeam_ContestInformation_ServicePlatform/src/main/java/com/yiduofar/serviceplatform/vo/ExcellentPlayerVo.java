package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/1 22:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExcellentPlayerVo {

    /**
     * 头像
     */
    private String userHead;
    /**
     * 名字
     */
    private String userName;
    /**
     * 参赛次数
     */
    Integer joinedNum;
}
