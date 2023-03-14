package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 大厅返回的PageVo
 * @author HuiBBao
 * @create 2023/2/21 21:02
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CompetitionPageTotal {
    List<Competition> page;
    Long total;
}
