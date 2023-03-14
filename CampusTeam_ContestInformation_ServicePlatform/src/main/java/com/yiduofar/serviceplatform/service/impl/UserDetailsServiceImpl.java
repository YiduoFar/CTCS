package com.yiduofar.serviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiduofar.serviceplatform.domain.LoginUser;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.MenuMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author HuiBBao
 * @create 2023/2/7 19:50
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = userMapper.selectOne(wrapper);
        // 如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(user)) {
            throw new InternalAuthenticationServiceException("未注册的手机号码");
        }
        // 如果该用户被封禁
        if (user.getStatus() == 2) {
            throw new InternalAuthenticationServiceException("您的账号被封禁，请联系管理员");
        }
        // 根据用户查询权限信息 添加到LoginUser中
        List<String> permissionKeyList = menuMapper.selectPermsByUserId(user.getId());
        // 封装成UserDetails对象返回
        return new LoginUser(user, permissionKeyList);
    }
}