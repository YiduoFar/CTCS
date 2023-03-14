package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/12 15:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsPageTotal {
    /**
     * page
     */
    List<News> page;
    /**
     * total
     */
    Long total;
}
