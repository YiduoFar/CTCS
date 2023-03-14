package com.yiduofar.serviceplatform.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.domain.UserCompetition;
import com.yiduofar.serviceplatform.service.UserCompetitionService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserCompetitionDeleteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserCompetition)表控制层
 *
 * @author makejava
 * @since 2023-02-14 15:35:58
 */
@RestController
@Slf4j
public class UserCompetitionController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private UserCompetitionService userCompetitionService;

    /**
     * 根据竞赛id、状态查询竞赛的 参与者 或 申请者列表（个人）
     * @param competitionId
     * @param status
     * @return
     */
    @GetMapping("/user_competition/selectUserListByCompetitionIdAndStatus")
    public ResponseResult selectUserListByCompetitionIdAndStatus(@RequestParam("competitionId") Integer competitionId,
                                                                 @RequestParam("status") Integer status) {
        return userCompetitionService.selectUserListByCompetitionIdAndStatus(competitionId, status);
    }


    /**
     * 删除一条用户竞赛关系
     * userCompetitionDeleteVo.type为操作类型 剔除3 婉拒2 退出1
     * @param userCompetitionDeleteVo
     * @return
     */
    @PostMapping("/user_competition/deleteOne")
    public ResponseResult deleteOne(UserCompetitionDeleteVo userCompetitionDeleteVo) {
        return userCompetitionService.deleteUserCompetition(userCompetitionDeleteVo);
    }

    /**
     * 更新用户竞赛关系（同意申请）
     * @param userCompetition
     * @return
     */
    @PostMapping("/user_competition/updateStatusById")
    public ResponseResult updateStatusById(UserCompetition userCompetition) {
        return userCompetitionService.updateStatusById(userCompetition);
    }

    /**
     * 新增用户竞赛关系（申请）
     * @param userCompetition
     * @return
     */
    @PostMapping("/user_competition/insertOne")
    public ResponseResult insertOne(UserCompetition userCompetition) {
        return userCompetitionService.insertOne(userCompetition);
    }

    /**
     * 查询一条用户竞赛关系
     * @param userCompetition
     * @return
     */
    @PostMapping("/user_competition/selectOne")
    public ResponseResult selectOne(UserCompetition userCompetition) {
        return userCompetitionService.selectOne(userCompetition);
    }


}

