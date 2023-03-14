package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yiduofar.serviceplatform.domain.Announcement;
import com.yiduofar.serviceplatform.domain.News;
import com.yiduofar.serviceplatform.service.AnnouncementService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * (Announcement)表控制层
 *
 * @author makejava
 * @since 2023-03-10 22:29:51
 */
@RestController
public class AnnouncementController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AnnouncementService announcementService;

    /**
     * 获取所有公告
     * @return
     */
    @GetMapping("/announcement/selectAll")
    public ResponseResult selectAll() {
        return announcementService.selectAll();
    }

    /**
     * 分页
     * 获取公告
     * @return
     */
    @PostMapping("/show/announcement/selectPage")
    public ResponseResult selectPage(QueryVo queryVo) {
        return announcementService.selectPage(queryVo);
    }

    /**
     * 根据id查询公告
     * @param announcementId
     * @return
     */
    @GetMapping("/announcement/selectById")
    public ResponseResult selectById(Integer announcementId) {
        return announcementService.selectById(announcementId);
    }

    /**
     * 发布公告
     * @param announcement
     * @return
     */
    @PostMapping("/announcement/insertOne")
    public ResponseResult insertOne(@Valid Announcement announcement) {
        return announcementService.insertOne(announcement);
    }

    /**
     * 删除公告
     * @param announcement
     * @return
     */
    @PostMapping("/announcement/deleteOne")
    public ResponseResult deleteOne(Announcement announcement) {
        return announcementService.deleteOne(announcement);
    }


}

