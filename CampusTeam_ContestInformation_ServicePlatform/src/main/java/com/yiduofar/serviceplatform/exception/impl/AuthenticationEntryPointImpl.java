package com.yiduofar.serviceplatform.exception.impl;

import com.alibaba.fastjson.JSON;
import com.yiduofar.serviceplatform.util.WebUtil;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:43
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = ResponseResult.error(ResponseResultEnum.HTTP_STATUS_UNAUTHORIZED);
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}