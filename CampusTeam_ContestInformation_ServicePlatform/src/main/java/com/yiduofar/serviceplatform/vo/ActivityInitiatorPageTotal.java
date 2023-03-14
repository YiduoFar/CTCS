package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/2/28 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityInitiatorPageTotal {
    /**
     * 分页数据
     */
    List<ActivityInitiatorVo> page;
    /**
     * 总页数
     */
    Long total;
}
