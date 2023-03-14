package com.yiduofar.serviceplatform.socketio;

/**
 * @author HuiBBao
 * @create 2023/3/12 23:34
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageInfoStructure {
    //消息类型
    private String msgType;
    //消息内容
    private String msgContent;

}

