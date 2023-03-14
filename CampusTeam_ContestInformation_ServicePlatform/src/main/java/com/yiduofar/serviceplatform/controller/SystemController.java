package com.yiduofar.serviceplatform.controller;

import com.yiduofar.serviceplatform.service.SystemService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuiBBao
 * @create 2023/3/10 11:15
 */
@RestController
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * 全平台管理
     * @param systemDomainVo
     * @return
     */
    @PostMapping("/system/domain/manage")
    @PreAuthorize("hasAuthority('system:all')")
    public ResponseResult systemManage(SystemDomainVo systemDomainVo) {
        System.out.println(systemDomainVo.toString());
        // 全平台查询
        if (systemDomainVo.getActionType().equals("select")) {
            return systemService.manageSelect(systemDomainVo);
        }
        // 全平台删除
        if (systemDomainVo.getActionType().equals("delete")) {
            return systemService.manageDelete(systemDomainVo);
        }
        return ResponseResult.error(ResponseResultEnum.ERROR);
    }

}
