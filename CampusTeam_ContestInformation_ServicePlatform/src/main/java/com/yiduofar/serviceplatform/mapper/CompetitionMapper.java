package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.vo.SlideShowVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author HuiBBao
* @description 针对表【competition】的数据库操作Mapper
* @createDate 2023-02-13 14:54:49
* @Entity com.yiduofar.serviceplatform.domain.Competition
*/
public interface CompetitionMapper extends BaseMapper<Competition> {
    /**
     * 连表时的分页用法
     * 多表关联SQL语句自主实现，分页以及条件使用MybatisPlus
     * @Param(Constants.WRAPPER) 注解：让Wrapper组合到自定义的SQL语句的${ew.customSqlSegment}中
     * @param page
     * @param queryWrapper
     * @return
     */
    @MapKey("id")
    IPage<Map<String, Object>> selectCompetitionByUserIdAndKeyWord(Page page, @Param(Constants.WRAPPER) Wrapper queryWrapper);

    @MapKey("id")
    IPage<Map<String, Object>> selectPageByIdAndKeyword(Page page, @Param("ids") Set<Integer> ids, @Param("status")  Integer status, @Param("keyword") String keyword);

    /**
     * 不连表时的分页用法
     * @param page
     * @param queryWrapper
     * @return
     */
    List<Competition> selectPage2(Page page,@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    List<SlideShowVo> selectSlideShowVo();

}




