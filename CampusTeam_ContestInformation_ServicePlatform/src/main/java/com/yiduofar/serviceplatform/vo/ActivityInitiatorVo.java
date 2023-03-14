package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Activity;
import com.yiduofar.serviceplatform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/28 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityInitiatorVo {
    Activity activity;
    User initiator;
}
