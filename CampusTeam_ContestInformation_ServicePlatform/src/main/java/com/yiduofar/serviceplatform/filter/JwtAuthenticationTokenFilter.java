package com.yiduofar.serviceplatform.filter;

import com.yiduofar.serviceplatform.domain.LoginUser;
import com.yiduofar.serviceplatform.util.JwtUtil;
import com.yiduofar.serviceplatform.util.RedisCache;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import io.jsonwebtoken.Claims;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * @author HuiBBao
 * @create 2022/10/5 13:28
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("请求拦截: " + request.getRequestURI());
        /**
         * 获取token
         */
        String token = request.getHeader("authorization");
        log.info("请求中携带的token:" + token);
        if (!StringUtils.hasText(token)) {
            // 放行
            log.info("放行了token为null的请求: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            // 必须return 否则会回来再走一次
            return;
        }
        /**
         * 解析token
         */
        String phone;
        try {
            Claims claims = JwtUtil.parseJWT(token.split(" ")[1]);
            phone = claims.getSubject();
        } catch (Exception e) {
            log.info("token解析出错");
            throw new RuntimeException("token非法");
        }
        /**
         * 从redis中获取用户信息
         * 注销时，会在这一步卡住，redis中信息已被删除
         */
        String redisKey = "login:" + phone;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            log.info("用户登录超时");
            throw new RuntimeException(ResponseResultEnum.TOKEN_ERROR.getMessage());
        }
        /**
         * 存入SecurityContextHolder
         * 后面的filter从存入SecurityContextHolder获取认证的用户信息
         */
        // 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 其他服务中可以像这样随时可以使用当前的用户信息
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // String phone = loginUser.getUser().getPhone();

        /**
         * 放行
         */
        filterChain.doFilter(request, response);
        log.info("放行了..来自" + loginUser.getUsername() + "携带token的请求");
    }
}