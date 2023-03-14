package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.vo.ResponseResult;

/**
 * @author HuiBBao
 * @create 2023/2/8 0:55
 */
public interface VerifyService {

    ResponseResult regVerifyUserName(String userName);

    ResponseResult regVerifyPhone(String phone);

    ResponseResult regVerifyNumber(String number);

    ResponseResult regVerifyEmail(String email);

}
