package com.yiduofar.serviceplatform.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author HuiBBao
 * @create 2023/3/9 8:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompetitionStage {

    //TODO @JSONField 不生效，待解答

    @JSONField(ordinal = 2)
    public String competitionStageName;

    @JSONField(ordinal = 1)
    public String competitionStageIntro;

    @JSONField(ordinal = 3)
    public Date competitionStageBeginTime;

    @JSONField(ordinal = 4, format = "yyyy", serialzeFeatures = SerializerFeature.WriteMapNullValue)
    public Date competitionStageEndTime;

}
