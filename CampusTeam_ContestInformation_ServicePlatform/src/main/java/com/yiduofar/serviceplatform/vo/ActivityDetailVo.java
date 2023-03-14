package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/2/28 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDetailVo {
    Activity activity;
    /**
     * 发布人
     */
    User initiator;
    /**
     * 文件
     */
    private List<ActivityFile> activityFileList;
    /**
     * 参赛者
     */
    private List<User> joinedUserList;

    /**
     * 参赛队伍
     */
    private List<Team> joinedTeamList;
}
