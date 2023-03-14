package com.yiduofar.serviceplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.User;
import com.yiduofar.serviceplatform.service.UserService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;
import com.yiduofar.serviceplatform.vo.UserHeadFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2023-02-07 13:33:56
 */
@RestController
@Slf4j
public class UserController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    @GetMapping("/user/selectOneByPhone")
    public ResponseResult selectOneByPhone(String phone) {
        return userService.selectOneByPhone(phone);
    }

    /**
     * 根据id编辑用户信息
     * @param user
     * @return
     */
    @PostMapping("/user/updateOneById")
    public ResponseResult updateOneById(@Valid User user) {
        return userService.updateOneById(user);
    }

    /**
     * 根据用户id查询用户名
     * @param id
     * @return
     */
    @GetMapping("/user/selectUserNameById")
    public ResponseResult selectUserNameById(Integer id) {
        return userService.selectUserNameById(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 根据用户id上传头像图片
     * @param file
     * @param id
     * @return
     */
    @PostMapping("/user/uploadHeadImgById")
    public ResponseResult uploadHeadImgById(@RequestParam(value = "file") MultipartFile file,
                                            @RequestParam(value = "id") Integer id) throws IOException {
        return userService.uploadHeadImgById(file, id);
    }

    /**
     * 查询用户详情Vo
     */
    @GetMapping("/user/selectUserDetailVoById")
    public ResponseResult selectUserDetailVo(Integer userId) {
        return userService.selectUserDetailVo(userId);
    }

    /**
     * 解封用户
     * @return
     */
    @PostMapping("/user/unseal")
    public ResponseResult unseal(SystemDomainVo systemDomainVo) {
        return userService.unseal(systemDomainVo);
    }



}

