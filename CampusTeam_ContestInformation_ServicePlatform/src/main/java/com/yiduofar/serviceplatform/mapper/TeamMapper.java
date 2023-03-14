package com.yiduofar.serviceplatform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiduofar.serviceplatform.domain.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.vo.TeamStarVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author HuiBBao
* @description 针对表【team】的数据库操作Mapper
* @createDate 2023-02-20 20:47:28
* @Entity com.yiduofar.serviceplatform.domain.Team
*/
public interface TeamMapper extends BaseMapper<Team> {

    List<Integer> selectIdsByCaptionId(@Param("userId") Integer userId);

    List<Team> selectPage2(Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    List<TeamStarVo> selectTeamStar();

}




