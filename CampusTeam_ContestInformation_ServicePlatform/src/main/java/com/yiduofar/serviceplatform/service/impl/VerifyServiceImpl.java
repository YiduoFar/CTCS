package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.VerifyService;
import com.yiduofar.serviceplatform.util.JwtUtil;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HuiBBao
 * @create 2023/2/8 0:57
 */
@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户名唯一性校验
     * @param userName
     * @return
     */
    @Override
    public ResponseResult regVerifyUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new GlobalException(ResponseResultEnum.REG_USERNAME_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 电话号码唯一性校验
     * @param phone
     * @return
     */
    @Override
    public ResponseResult regVerifyPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new GlobalException(ResponseResultEnum.REG_PHONE_ERROR);
        }
        return ResponseResult.success();
    }


    /**
     * 学号/工号唯一性校验
     * @param number
     * @return
     */
    @Override
    public ResponseResult regVerifyNumber(String num) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("num", num);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new GlobalException(ResponseResultEnum.REG_NUMBER_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 邮箱唯一性校验
     * @param email
     * @return
     */
    @Override
    public ResponseResult regVerifyEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new GlobalException(ResponseResultEnum.REG_EMAIL_ERROR);
        }
        return ResponseResult.success();
    }

}
