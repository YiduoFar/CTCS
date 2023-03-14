package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.domain.Competition;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HuiBBao
 * @description 针对表【activity】的数据库操作Mapper
 * @createDate 2023-02-27 00:18:57
 * @Entity com.yiduofar.serviceplatform.domain.Activity
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    @MapKey("id")
    IPage<Map<String, Object>> selectMyActivityByKeyWordAndStatus(Page page, @Param("ids") Set<Integer> ids, @Param("status") Integer status, @Param("keyword") String keyword);

    /**
     * 不连表时的分页用法
     * @param page
     * @param queryWrapper
     * @return
     */
    List<Activity> selectPage2(Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    List<Activity> selectLatestActivity();
}




