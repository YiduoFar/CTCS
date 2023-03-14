package com.yiduofar.serviceplatform.mapper;

import com.yiduofar.serviceplatform.domain.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-02-21 01:40:18
* @Entity com.yiduofar.serviceplatform.domain.Message
*/
public interface MessageMapper extends BaseMapper<Message> {

    List<Message> selectMessageByUserIdAndTeamId(@Param("userId") Integer userId,
                                                 @Param("teamIds") List<Integer> teamIds);
}




