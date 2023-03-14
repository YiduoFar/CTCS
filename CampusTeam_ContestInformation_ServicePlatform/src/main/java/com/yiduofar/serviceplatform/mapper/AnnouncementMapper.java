package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【announcement】的数据库操作Mapper
* @createDate 2023-03-10 22:29:42
* @Entity com.yiduofar.serviceplatform.domain.Announcement
*/
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    List<Announcement> selectPage2(Page page);

}




