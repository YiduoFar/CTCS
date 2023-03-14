package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.UserLoginVo;
import com.yiduofar.serviceplatform.vo.UserRegVo;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:45
 */
public interface LoginService {
    ResponseResult login(UserLoginVo userLoginVo);

    ResponseResult reg(UserRegVo userRegVo);

    ResponseResult logout();
}
