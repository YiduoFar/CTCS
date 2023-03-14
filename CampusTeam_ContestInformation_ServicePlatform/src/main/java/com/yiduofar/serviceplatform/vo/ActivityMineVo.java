package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 活动 领域对象
 * @author HuiBBao
 * @create 2023/2/27 0:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityMineVo {

    Activity activity;
    Integer teamId;
    String teamName;
    String teamLogo;

    public ActivityMineVo(Activity a) {
        this.activity = a;
        this.teamId = null;
        this.teamName = null;
        this.teamLogo = null;
    }

}
