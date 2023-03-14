package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * ’我的竞赛‘返回的PageVo
 * @author HuiBBao
 * @create 2023/2/22 3:28
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CompetitionPageTotal2 {
    /**
     * 分页数据
     */
    List<CompetitionVo2> page;
    /**
     * 总页数
     */
    Long total;
}