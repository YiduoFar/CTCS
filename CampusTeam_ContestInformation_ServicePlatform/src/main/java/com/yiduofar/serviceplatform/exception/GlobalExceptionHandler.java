package com.yiduofar.serviceplatform.exception;

import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author HuiBBao
 * @create 2022/10/4 14:31
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            log.info("进来处理类GlobalException了。。");
            GlobalException ex = (GlobalException) e;
            return ResponseResult.error(ex.getResponseResultEnum());
        } else if (e instanceof AccessDeniedException) {
            // 捕获权限不足
            log.info(ResponseResultEnum.HTTP_STATUS_FORBIDDEN.getMessage());
            return ResponseResult.error(ResponseResultEnum.HTTP_STATUS_FORBIDDEN);
        } else if (e instanceof BindException) {
            // 参数校验异常
            BindException ex = (BindException) e;
            ResponseResult respBean = ResponseResult.error(ResponseResultEnum.BIND_ERROR);
            System.out.println(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            if (ex.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals("不能为null")) {
                respBean.setMessage("请输入完整信息");
                return respBean;
            }
            if (ex.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals("不能为空")) {
                respBean.setMessage("请输入完整信息");
                return respBean;
            }
            respBean.setMessage("参数校验异常：" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        } else if (e instanceof InternalAuthenticationServiceException) {
            // 捕获登录过程中抛出的其他错误
            InternalAuthenticationServiceException ex = (InternalAuthenticationServiceException) e;
            ResponseResult bean = ResponseResult.error(ResponseResultEnum.LOGIN_ERROR);
            bean.setMessage(ex.getMessage());
            return bean;
        } else if (e instanceof BadCredentialsException) {
            // 捕获用户名或密码错误
            BadCredentialsException ex = (BadCredentialsException) e;
            return ResponseResult.error(ResponseResultEnum.LOGIN_ERROR);
        }
        e.printStackTrace();
        return ResponseResult.error(ResponseResultEnum.ERROR);
    }


}