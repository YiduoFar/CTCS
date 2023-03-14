package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;

/**
 * @author HuiBBao
 * @create 2023/3/10 11:16
 */
public interface SystemService {


    ResponseResult manageDelete(SystemDomainVo systemDomainVo);

    ResponseResult manageSelect(SystemDomainVo systemDomainVo);

}
