package com.yiduofar.serviceplatform.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * @author HuiBBao
 * @create 2023/3/9 10:44
 */
@Component
public class UUIDUtil {

    /**
     * 生成5位随机码
     * @return
     */
    public String createInviteCode() {
        // 随机下标
        int nextInt = new Random().nextInt(25);
        // 6位随机码
        return getUUID().substring(nextInt, nextInt + 6);
    }

    /**
     * 生成3位随机码
     * @return
     */
    public String create3Code() {
        // 随机下标
        int nextInt = new Random().nextInt(30);
        // 3位随机码
        return getUUID().substring(nextInt, nextInt + 3);
    }

    /**
     * 获取32位默认长度的UUID
     * @return
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
