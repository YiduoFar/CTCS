package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【article】的数据库操作Mapper
* @createDate 2023-03-06 15:55:48
* @Entity com.yiduofar.serviceplatform.domain.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectPage2(Page<Article> page,@Param(Constants.WRAPPER) QueryWrapper<Object> wrapper);

}




