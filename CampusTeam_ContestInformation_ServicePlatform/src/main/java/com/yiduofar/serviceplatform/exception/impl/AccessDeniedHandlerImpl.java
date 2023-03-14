package com.yiduofar.serviceplatform.exception.impl;

import com.alibaba.fastjson.JSON;
import com.yiduofar.serviceplatform.util.WebUtil;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:41
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult result = ResponseResult.error(ResponseResultEnum.HTTP_STATUS_FORBIDDEN);
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}