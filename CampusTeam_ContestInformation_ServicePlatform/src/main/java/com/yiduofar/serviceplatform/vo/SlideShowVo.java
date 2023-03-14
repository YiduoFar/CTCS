package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/24 2:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SlideShowVo {
    Integer competitionId;
    String competitionName;
    String competitionCover;
}
