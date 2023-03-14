package com.yiduofar.serviceplatform.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author HuiBBao
 * @create 2023/3/9 9:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityStage {

    //TODO @JSONField 不生效，待解答

    public String activityStageName;

    public String activityStageIntro;

    public Date activityStageBeginTime;

    public Date activityStageEndTime;


}
