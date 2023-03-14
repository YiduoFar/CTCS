package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;

/**
 * @author HuiBBao
 * @description 针对表【message】的数据库操作Service
 * @createDate 2023-02-21 01:40:18
 */
public interface MessageService extends IService<Message> {

    void sendMessageToUser(Integer userId, String content, String url, Integer type);

    void sendMessageToTeam(Integer teamId, String content, String url, Integer type);

    void sendMessageToUser(Integer[] userId, String content, String url, Integer type);

    void sendMessageToTeam(Integer[] teamId, String content, String url, Integer type);

    ResponseResult selectMessageByUserId(Integer userId);

    ResponseResult deleteMessageByUserId(Integer userId);
}
