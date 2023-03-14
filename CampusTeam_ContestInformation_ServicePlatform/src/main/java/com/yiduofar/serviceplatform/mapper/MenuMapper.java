package com.yiduofar.serviceplatform.mapper;

/**
 * @author HuiBBao
 * @create 2023/2/10 1:28
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiduofar.serviceplatform.domain.Menu;

import java.util.List;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Integer id);
}