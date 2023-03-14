package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiduofar.serviceplatform.domain.Competition;
import com.yiduofar.serviceplatform.domain.Message;
import com.yiduofar.serviceplatform.domain.Team;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.TeamMapper;
import com.yiduofar.serviceplatform.service.MessageService;
import com.yiduofar.serviceplatform.mapper.MessageMapper;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author HuiBBao
 * @description 针对表【message】的数据库操作Service实现
 * @createDate 2023-02-21 01:40:18
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    TeamMapper teamMapper;

    /**
     * 给用户发消息
     * @param userId
     * @param content
     * @param url
     * @param type
     */
    @Override
    public void sendMessageToUser(Integer userId, String content, String url, Integer type) {
        // 发送消息
        Message message = new Message();
        message.setUserId(userId);
        message.setMessageContent(content);
        message.setMessageTime(new Date());
        message.setMessageUrl(url);
        message.setMessageType(type);
        // 0个人消息 1队伍消息
        message.setMessageObjectType(0);
        int count = messageMapper.insert(message);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.MESSAGE_ERROR);
        }
    }

    /**
     * 给用户组发消息
     * @param userId
     * @param content
     * @param url
     * @param type
     */
    @Override
    public void sendMessageToUser(Integer[] userId, String content, String url, Integer type) {
        // 群发消息
        for (int id : userId) {
            Message message = new Message();
            message.setUserId(id);
            message.setMessageContent(content);
            message.setMessageTime(new Date());
            message.setMessageUrl(url);
            message.setMessageType(type);
            // 0个人消息 1队伍消息
            message.setMessageObjectType(0);
            int count = messageMapper.insert(message);
            if (count != 1) {
                throw new GlobalException(ResponseResultEnum.MESSAGE_ERROR);
            }
        }
    }

    /**
     * 给队伍发消息
     * @param teamId
     * @param content
     * @param url
     * @param type
     */
    @Override
    public void sendMessageToTeam(Integer teamId, String content, String url, Integer type) {
        // 发送消息
        Message message = new Message();
        message.setTeamId(teamId);
        message.setMessageContent(content);
        message.setMessageTime(new Date());
        message.setMessageUrl(url);
        message.setMessageType(type);
        // 0个人消息 1队伍消息
        message.setMessageObjectType(1);
        int count = messageMapper.insert(message);
        if (count != 1) {
            throw new GlobalException(ResponseResultEnum.MESSAGE_ERROR);
        }
    }

    /**
     * 给队伍组发消息
     * @param teamId
     * @param content
     * @param url
     * @param type
     */
    @Override
    public void sendMessageToTeam(Integer[] teamId, String content, String url, Integer type) {
        // 群发消息
        for (int id : teamId) {
            Message message = new Message();
            message.setTeamId(id);
            message.setMessageContent(content);
            message.setMessageTime(new Date());
            message.setMessageUrl(url);
            message.setMessageType(type);
            // 0个人消息 1队伍消息
            message.setMessageObjectType(1);
            int count = messageMapper.insert(message);
            if (count != 1) {
                throw new GlobalException(ResponseResultEnum.MESSAGE_ERROR);
            }
        }
    }


    /**
     * 根据用户id查询消息
     * @param userId
     * @return
     */
    @Override
    public ResponseResult selectMessageByUserId(Integer userId) {
        // 用userId查teamId
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_caption_id", userId);
        List<Team> teams = teamMapper.selectList(queryWrapper);
        List<Message> messages;
        if (CollectionUtils.isEmpty(teams)) {
            QueryWrapper<Message> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            messages = messageMapper.selectList(wrapper);
        } else {
            List<Integer> teamIds = new ArrayList<>();
            for (Team team : teams) {
                teamIds.add(team.getTeamId());
            }
            messages = messageMapper.selectMessageByUserIdAndTeamId(userId, teamIds);
        }
        if (CollectionUtils.isEmpty(messages)) {
            throw new GlobalException(ResponseResultEnum.NULL_MESSAGE_ERROR);
        }
        // 根据id倒序排序
        Collections.sort(messages, (o1, o2) -> {
            Integer id1 = o1.getMessageId();
            Integer id2 = o2.getMessageId();
            return id2.compareTo(id1);
        });
        return ResponseResult.success(messages);
    }

    /**
     * 根据用户id清空消息（删除）
     * @param userId
     * @return
     */
    @Override
    public ResponseResult deleteMessageByUserId(Integer userId) {
        // 用userId查teamId
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_caption_id", userId);
        List<Team> teams = teamMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(teams)) {
            messageMapper.delete(new QueryWrapper<Message>().eq("user_id", userId));
        } else {
            for (Team team : teams) {
                messageMapper.delete(new QueryWrapper<Message>().eq("team_id", team.getTeamId()));
            }
            messageMapper.delete(new QueryWrapper<Message>().eq("user_id", userId));
        }
        return ResponseResult.success();
    }
}




