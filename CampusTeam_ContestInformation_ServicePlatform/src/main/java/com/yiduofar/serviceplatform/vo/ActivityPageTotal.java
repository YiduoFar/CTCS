package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/2/27 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityPageTotal {
    /**
     * 分页数据
     */
    List<Activity> page;
    /**
     * 总页数
     */
    Long total;
}
