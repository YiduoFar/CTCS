package com.yiduofar.serviceplatform.service;

import com.yiduofar.serviceplatform.domain.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiduofar.serviceplatform.vo.QueryVo;
import com.yiduofar.serviceplatform.vo.ResponseResult;

/**
* @author HuiBBao
* @description 针对表【announcement】的数据库操作Service
* @createDate 2023-03-10 22:29:42
*/
public interface AnnouncementService extends IService<Announcement> {

    ResponseResult selectAll();

    ResponseResult selectPage(QueryVo queryVo);

    ResponseResult selectById(Integer announcementId);

    ResponseResult insertOne(Announcement announcement);

    ResponseResult deleteOne(Announcement announcement);

}
