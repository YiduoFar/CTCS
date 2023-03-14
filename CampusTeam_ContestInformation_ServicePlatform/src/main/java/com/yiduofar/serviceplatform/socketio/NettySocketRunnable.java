package com.yiduofar.serviceplatform.socketio;

/**
 * @author HuiBBao
 * @create 2023/3/12 23:34
 */
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
@Slf4j
public class NettySocketRunnable implements CommandLineRunner {
    private final SocketIOServer server;

    @Autowired
    public NettySocketRunnable(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("--------------------前端socket.io通信启动成功！---------------------");
        server.start();
    }
}

