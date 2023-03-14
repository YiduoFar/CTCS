package com.yiduofar.serviceplatform.controller;

import com.yiduofar.serviceplatform.service.LoginService;
import com.yiduofar.serviceplatform.service.VerifyService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserLoginVo;
import com.yiduofar.serviceplatform.vo.UserRegVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author HuiBBao
 * @create 2022/10/2 11:39
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    VerifyService verifyService;

    /**
     * 登录
     * @param userLoginVo
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@Valid UserLoginVo userLoginVo) {
        return loginService.login(userLoginVo);
    }

    /**
     * 注销
     * @return
     */
    @PostMapping("/log_out")
    public ResponseResult logOut() {
        return loginService.logout();
    }

    /**
     * 注册，及其所需参数的校验接口
     * @param userRegVo
     * @return
     */
    @PostMapping("/reg")
    public ResponseResult reg(@Valid UserRegVo userRegVo) {
        return loginService.reg(userRegVo);
    }

    /**
     * 用户名唯一性校验
     * @param userName
     * @return
     */
    @GetMapping("/reg/verify/user_name")
    public ResponseResult regVerifyUserName(String userName) {
        return verifyService.regVerifyUserName(userName);
    }

    /**
     * 电话号码唯一性校验
     * @param phone
     * @return
     */
    @GetMapping("/reg/verify/phone")
    public ResponseResult regVerifyPhone(String phone) {
        return verifyService.regVerifyPhone(phone);
    }

    /**
     * 学号/工号唯一性校验
     * @param number
     * @return
     */
    @GetMapping("/reg/verify/number")
    public ResponseResult regVerifyNumber(String number) {
        return verifyService.regVerifyNumber(number);
    }

    /**
     * 邮箱唯一性校验
     * @param email
     * @return
     */
    @GetMapping("/reg/verify/email")
    public ResponseResult regVerifyEmail(String email) {
        return verifyService.regVerifyEmail(email);
    }

    /**
     * token校验，空请求
     * security会帮我们解析携带的token 失效会通过AuthenticationEntryPoint返回
     * @param
     * @return
     */
    @GetMapping("/token/check")
    public ResponseResult tokenCheck() {
        return ResponseResult.success();
    }

}
