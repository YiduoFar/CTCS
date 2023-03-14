package com.yiduofar.campusteam_contestinformation_serviceplatform;
//package com.yiduofar.serviceplatform;

import com.yiduofar.serviceplatform.CampusTeamContestInformationServicePlatformApplication;
import com.yiduofar.serviceplatform.controller.NewsController;
import com.yiduofar.serviceplatform.schedule.ScheduleTask;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试前关停socket.io服务
 */
@SpringBootTest(classes = CampusTeamContestInformationServicePlatformApplication.class)
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

//    @Test
//    void test() {
//        QueryVo queryVo = new QueryVo();
//        queryVo.setCurrent(1);
//        queryVo.setSize(5);
//        ResponseResult responseResult = newsController.selectPage(queryVo);
//        System.out.println(responseResult.getData());
//    }

}
