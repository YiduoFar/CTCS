package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/5 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamCaptainPageTotal {
    List<TeamMemVo3> page;
    Long total;
}
