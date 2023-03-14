package com.yiduofar.serviceplatform.controller;

import com.yiduofar.serviceplatform.schedule.ScheduleTask;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserPointVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取计算数据的controller
 *
 * @author HuiBBao
 * @create 2023/2/24 16:06
 */
@RestController
@Slf4j
public class DataCountController {

    @Autowired
    ScheduleTask scheduleTask;

    /**
     * 获取竞赛积分排行数据
     *
     * @return
     */
    @GetMapping("/show/getUserPointVoList")
    public ResponseResult getUserPointVoList() {
        // 项目启动时先进行一次计算
        if (CollectionUtils.isEmpty(ScheduleTask.userPointVoList)) {
            scheduleTask.countPoint();
        }
        return ResponseResult.success(ScheduleTask.userPointVoList);
    }

    /**
     * 获取竞赛积分排行数据
     *
     * @return
     */
    @GetMapping("/show/getExcellentPlayerVoList")
    public ResponseResult getExcellentPlayerVoList() {
        // 项目启动时先进行一次计算
        if (CollectionUtils.isEmpty(ScheduleTask.excellentPlayerVoList)) {
            scheduleTask.countExcellentPlayer();
        }
        return ResponseResult.success(ScheduleTask.excellentPlayerVoList);
    }

    /**
     * 查询优秀队伍
     *
     * @return
     */
    @GetMapping("/show/team/getExcellentTeam")
    public ResponseResult getExcellentTeam() {
        // 项目启动时先进行一次计算
        if (CollectionUtils.isEmpty(ScheduleTask.excellentTeamVoList)) {
            scheduleTask.countExcellentTeam();
        }
        return ResponseResult.success(ScheduleTask.excellentTeamVoList);
    }


}
