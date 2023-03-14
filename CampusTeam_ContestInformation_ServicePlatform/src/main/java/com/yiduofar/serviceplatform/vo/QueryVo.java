package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询实体类
 * @author HuiBBao
 * @create 2023/2/13 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryVo {


    /**
     * userid
     */
    Integer userId;
    /**
     * status
     */
    Integer status;


    /**
     * 当前页
     */
    Integer current;
    /**
     * 一页多少条数据
     */
    Integer size;
    /**
     * 关键字
     */
    String keyword;
}