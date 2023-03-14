package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author HuiBBao
 * @create 2023/2/22 3:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionVo2 {
    Competition competition;
    Integer teamId;
    String teamName;
    String teamLogo;

    public CompetitionVo2(Competition c) {
        this.competition = c;
        this.teamId = null;
        this.teamName = null;
        this.teamLogo = null;
    }
}
