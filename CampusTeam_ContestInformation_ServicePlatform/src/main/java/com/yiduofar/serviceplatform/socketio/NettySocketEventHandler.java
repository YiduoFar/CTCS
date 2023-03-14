package com.yiduofar.serviceplatform.socketio;

/**
 * @author HuiBBao
 * @create 2023/3/12 23:31
 */

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.yiduofar.serviceplatform.domain.LoginUser;
import com.yiduofar.serviceplatform.domain.Message;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.util.JwtUtil;
import com.yiduofar.serviceplatform.util.RedisCache;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class NettySocketEventHandler {
    public static ConcurrentMap<String, SocketIOClient> socketIOClientMap =
            new ConcurrentHashMap<>();

    @Autowired
    private RedisCache redisCache;

    //socket事件消息接收入口
    @OnEvent(value = "message_event") //value值与前端自行商定
    public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfoStructure data) throws Exception {
        //根据msgType类别进行数据类型判断,
        if (data.getMsgType().equals("XXXXXData")) { //数据类型标识
            client.sendEvent("message_event", "已成功接收数据"); //向前端发送接收数据成功标识

            //data.getMsgContent()获取前端推送数据
            //......这里可填写接收数据后的相关业务逻辑代码
        }
    }

    //socket添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // 拿到前端发来的用户标识 socket.io.opts.query = "token=" + token
        String redisKey = getKeyFromToken(client.getHandshakeData().getSingleUrlParam("token"));
        // 从map中移除SocketIOClient
        socketIOClientMap.remove(redisKey);
        // 断开连接
        client.disconnect();
        log.info("--------------------客户端已断开连接--------------------");
    }

    //socket添加connect事件，当客户端发起连接时调用
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client != null) {
            // 拿到前端发来的用户标识 socket.io.opts.query = "token=" + token
            String redisKey = getKeyFromToken(client.getHandshakeData().getSingleUrlParam("token"));
            // 存储SocketIOClient，用于向不同客户端发送消息
            socketIOClientMap.put(redisKey, client);
            log.info("--------------------客户端连接成功---------------------");
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 向单一用户发送消息
     * @param redisKey
     * @param message
     */
    public static void sendToUser(String redisKey, MessageInfoStructure message) {
        log.info("向用户 " + redisKey + " 发送了消息 " + message.getMsgContent());
        for (Map.Entry<String, SocketIOClient> entry : socketIOClientMap.entrySet()) {
            if (entry.getKey().equals(redisKey)) {
                entry.getValue().sendEvent(message.getMsgType(), message.getMsgContent());
            }
        }
    }

    /**
     * 广播 新闻与公告
     * @param message
     */
    public static void sendBroadcast(Message message) {
        // 向已连接的所有客户端发送数据,map实现客户端的存储
        for (SocketIOClient client : socketIOClientMap.values()) {
            if (client.isChannelOpen()) {
                client.sendEvent("article_event", message);
            }
        }
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public String getKeyFromToken(String token) {
        // 解析token
        String phone;
        try {
            Claims claims = JwtUtil.parseJWT(token.split(" ")[1]);
            phone = claims.getSubject();
        } catch (Exception e) {
            log.info("token解析出错");
            throw new RuntimeException("token非法");
        }
        // 从redis中获取用户信息 注销后，会在这一步卡住，redis中信息已被删除
        String redisKey = "login:" + phone;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            log.info("用户登录超时");
            throw new GlobalException(ResponseResultEnum.TOKEN_ERROR);
        }
        return redisKey;
    }
}
