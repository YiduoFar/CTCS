package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.ResponseResult;
import com.yiduofar.serviceplatform.vo.SystemDomainVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author HuiBBao
* @description 针对表【user】的数据库操作Service
* @createDate 2023-02-07 13:32:23
*/
public interface UserService extends IService<User> {

    ResponseResult selectOneByPhone(String phone);

    ResponseResult updateOneById(User user);

    ResponseResult selectUserNameById(Integer id);

    ResponseResult uploadHeadImgById(MultipartFile file, Integer id) throws IOException;

    ResponseResult selectUserDetailVo(Integer userId);

    ResponseResult unseal(SystemDomainVo systemDomainVo);
}
