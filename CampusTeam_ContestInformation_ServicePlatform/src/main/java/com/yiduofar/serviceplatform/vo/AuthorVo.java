package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/3/7 18:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorVo {

    Integer userId;

    String userName;

    String userHead;

    /**
     * 参加的队伍数
     */
    Integer teamNum;

    /**
     * 发布的帖子数
     */
    Integer articleNum;

}
