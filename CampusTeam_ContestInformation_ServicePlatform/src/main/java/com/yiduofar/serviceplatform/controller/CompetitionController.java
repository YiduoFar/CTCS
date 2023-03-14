package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.service.CompetitionService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * (Competition)表控制层
 *
 * @author makejava
 * @since 2023-02-13 14:55:29
 */
@RestController
@Slf4j
public class CompetitionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CompetitionService competitionService;

    /**
     * 分页
     * 根据状查询竞赛
     * @param queryVo
     * @return
     */
    @PostMapping("/show/competition/selectCompetitionByStatus")
    public ResponseResult selectCompetitionByStatus(QueryVo queryVo) {
        return competitionService.selectCompetitionByStatus(queryVo);
    }

    /**
     * 分页
     * 根据关键字模糊查询竞赛
     * @param queryVo
     * @return
     */
    @PostMapping("/show/competition/selectCompetitionByKeyWord")
    public ResponseResult selectCompetitionByKeyWord(QueryVo queryVo) {
        return competitionService.selectCompetitionByKeyWord(queryVo);
    }


    /**
     * 分页
     * 根据用户id、关键字和状态查询竞赛
     * @param queryVo
     * @return
     */
    @PostMapping("/competition/selectCompetitionByUserIdAndKeyWord")
    @PreAuthorize("hasAuthority('system:competition')")
    public ResponseResult selectCompetitionByUserIdAndKeyWord(QueryVo queryVo) {
        return competitionService.selectCompetitionByUserIdAndKeyWord(queryVo);
    }


    /**
     * 发布竞赛（新增）
     * @param competition
     * @return
     */
    @PostMapping("/competition/insertCompetition")
    public ResponseResult insertCompetition(@Valid Competition competition) {
        System.out.println(competition.toString());
        return competitionService.insertCompetition(competition);
    }

    /**
     * 根据发布者id查询竞赛
     * @param undertaker3Id
     * @param keyword
     * @return
     */
    @GetMapping("/competition/selectCompetitionByUndertaker3Id")
    public ResponseResult selectCompetitionByUndertaker3Id(@RequestParam("undertaker3Id") Integer undertaker3Id,
                                                           @RequestParam("keyword")String keyword) {
        return competitionService.selectCompetitionByUndertaker3Id(undertaker3Id, keyword);
    }

    /**
     * 编辑竞赛（更新）
     * @param competition
     * @return
     */
    @PostMapping("/competition/updateCompetitionById")
    public ResponseResult updateCompetitionById(Competition competition) {
        return competitionService.updateCompetitionById(competition);
    }

    /**
     * 根据id查询竞赛
     * @param competitionId
     * @return
     */
    @GetMapping("/competition/selectCompetitionById")
    public ResponseResult selectCompetitionById(Integer competitionId) {
        return competitionService.selectCompetitionById(competitionId);
    }

    /**
     * 根据id查询竞赛Vo
     * @param competitionId
     * @return
     */
    @GetMapping("/competition/selectCompetitionVoById")
    public ResponseResult selectCompetitionVoById(Integer competitionId) {
        return competitionService.selectCompetitionVoById(competitionId);
    }

    /**
     * 下架竞赛（删除）
     * @param competitionId
     * @return
     */
    @PostMapping("/competition/deleteCompetitionById")
    public ResponseResult deleteCompetitionById(@RequestParam(value = "competitionId") Integer competitionId) {
        return competitionService.deleteCompetitionById(competitionId);
    }

    /**
     * 查询轮播图Vo（竞赛封面）
     * @return
     */
    @GetMapping("/show/competition/selectSlideShowVo")
    public ResponseResult selectSlideShowVo() {
        return competitionService.selectSlideShowVo();
    }


}

