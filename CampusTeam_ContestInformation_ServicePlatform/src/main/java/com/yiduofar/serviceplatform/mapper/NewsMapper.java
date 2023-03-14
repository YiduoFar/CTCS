package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【news】的数据库操作Mapper
* @createDate 2023-03-10 22:25:10
* @Entity com.yiduofar.serviceplatform.domain.News
*/
public interface NewsMapper extends BaseMapper<News> {

    List<News> selectPage2(Page page);

}




