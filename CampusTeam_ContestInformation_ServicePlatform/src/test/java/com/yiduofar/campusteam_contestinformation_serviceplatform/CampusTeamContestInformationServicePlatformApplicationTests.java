package com.yiduofar.serviceplatform;

import com.yiduofar.serviceplatform.controller.NewsController;
import com.yiduofar.serviceplatform.schedule.ScheduleTask;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusTeamContestInformationServicePlatformApplicationTests {

    @Autowired
    ScheduleTask scheduleTask;

    @Autowired
    NewsController newsController;

    @Test
    void contextLoads() {
        scheduleTask.updateTeamStar();
        scheduleTask.updateActivityCurrentStage();
        scheduleTask.updateCompetitionCurrentStage();
    }

    @Test
    void test() {
        QueryVo queryVo = new QueryVo();
        queryVo.setCurrent(1);
        queryVo.setSize(5);
        ResponseResult responseResult = newsController.selectPage(queryVo);
        System.out.println(responseResult.getData());
    }

}
