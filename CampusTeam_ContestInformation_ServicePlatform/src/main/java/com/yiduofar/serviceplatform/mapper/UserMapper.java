package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiduofar.serviceplatform.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author HuiBBao
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-07 13:32:23
* @Entity com.yiduofar.serviceplatform.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    int updateByOneById(User user);

    int updateUserHeadById(@Param("id") Integer id, @Param("filePath2") String filePath2);
}




