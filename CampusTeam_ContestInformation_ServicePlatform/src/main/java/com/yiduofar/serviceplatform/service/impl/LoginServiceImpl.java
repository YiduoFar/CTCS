package com.yiduofar.serviceplatform.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiduofar.serviceplatform.domain.LoginUser;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.domain.SysUserRole;
import com.yiduofar.serviceplatform.exception.GlobalException;
import com.yiduofar.serviceplatform.mapper.SysUserRoleMapper;
import com.yiduofar.serviceplatform.mapper.UserMapper;
import com.yiduofar.serviceplatform.service.LoginService;
import com.yiduofar.serviceplatform.socketio.MessageInfoStructure;
import com.yiduofar.serviceplatform.socketio.NettySocketEventHandler;
import com.yiduofar.serviceplatform.util.JwtUtil;
import com.yiduofar.serviceplatform.util.RedisCache;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.UserLoginVo;
import com.yiduofar.serviceplatform.vo.UserRegVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author HuiBBao
 * @create 2022/10/2 12:42
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    /**
     * 登录
     *
     * @param userLoginVo
     * @return
     */
    @Override
    public ResponseResult login(UserLoginVo userLoginVo) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginVo.getPhone(), userLoginVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new GlobalException(ResponseResultEnum.LOGIN_ERROR);
        }
        /**
         * token 身份校验
         * phone、name、identity配合前端所需
         */
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String id = loginUser.getUser().getId().toString();
        String phone = loginUser.getUser().getPhone();
        String userName = loginUser.getUser().getUserName();
        String userHead = loginUser.getUser().getUserHead();
        String identity = loginUser.getUser().getIdentity().toString();

        // 使用phone生成token
        String token = "Bearer " + JwtUtil.createJWT(phone);

        Object cacheObject = redisCache.getCacheObject("login:" + phone);
        // 如果当前已登录 authenticate已经存在于redis
        if (!Objects.isNull(cacheObject)) {

            // 提示当前用户已经在线
            // throw new GlobalException(ResponseResultEnum.LOGINED_ERROR);

            // 强制下线
            NettySocketEventHandler.sendToUser("login:" + phone, new MessageInfoStructure("logout_event", "强登"));

        } else {
            // authenticate存入redis
            redisCache.setCacheObject("login:" + phone, loginUser);
        }

        // 把token 以及其他信息 响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("id", id);
        map.put("phone", phone);
        map.put("userName", userName);
        map.put("userHead", userHead);
        map.put("identity", identity);

        return ResponseResult.success(map);
    }

    /**
     * 注册
     *
     * @param userRegVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult reg(UserRegVo userRegVo) {
        String phone = userRegVo.getPhone();
        String password = userRegVo.getPassword();
        // 根据手机号获取用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        // 用户已存在
        if (user != null) {
            throw new GlobalException(ResponseResultEnum.REG_ERROR);
        }
        // 新用户字段 属性装配
        user = new User();
        user.setUserName(userRegVo.getUserName());
        user.setPhone(userRegVo.getPhone());
        user.setNum(userRegVo.getNum());
        user.setCollege(userRegVo.getCollege());
        user.setEmail(userRegVo.getEmail());
        // 注册日期
        user.setRegDate(new Date());
        // 状态 1正常 0注销 2封禁
        user.setStatus(1);
        user.setSex(1);
        user.setIntro("此用户很懒什么也没留下");
        // 身份 0学生 1教师
        user.setIdentity(userRegVo.getNum().length() == 12 ? 0 : 1);
        // BCrypt加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        if (userMapper.insert(user) != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        /**
         * 用id注册权限表 1学生 2教师 3管理员
         */
        if (sysUserRoleMapper.insert(new SysUserRole(user.getId(), user.getIdentity() + 1)) != 1) {
            throw new GlobalException(ResponseResultEnum.DATABASE_ERROR);
        }
        return ResponseResult.success();
    }

    /**
     * 注销
     *
     * @return
     */
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String phone = loginUser.getUser().getPhone();
        redisCache.deleteObject("login:" + phone);
        return ResponseResult.success();
    }

}
