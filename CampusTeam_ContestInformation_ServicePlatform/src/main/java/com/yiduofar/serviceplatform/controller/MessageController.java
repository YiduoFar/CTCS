package com.yiduofar.serviceplatform.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Message)表控制层
 *
 * @author makejava
 * @since 2023-02-21 02:39:40
 */
@RestController
public class MessageController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private MessageService messageService;

    /**
     * 根据用户id查询消息
     * @param userId
     * @return
     */
    @GetMapping("/message/selectMessageByUserId")
    public ResponseResult selectMessageByUserId(Integer userId) {
        return messageService.selectMessageByUserId(userId);
    }

    /**
     * 根据用户id清空消息（删除）
     * @param userId
     * @return
     */
    @PostMapping("/message/deleteMessageByUserId")
    public ResponseResult deleteMessageByUserId(@RequestParam(value = "userId") Integer userId) {
        return messageService.deleteMessageByUserId(userId);
    }
}

