package com.yiduofar.serviceplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// 代替每个类都加Mapper
@MapperScan("com.yiduofar.serviceplatform.mapper")
public class CampusTeamContestInformationServicePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusTeamContestInformationServicePlatformApplication.class, args);
    }

}
