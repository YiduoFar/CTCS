package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.domain.Announcement;
import com.yiduofar.serviceplatform.domain.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author HuiBBao
 * @create 2023/3/12 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementPageTotal {
    /**
     * page
     */
    List<Announcement> page;
    /**
     * total
     */
    Long total;
}
