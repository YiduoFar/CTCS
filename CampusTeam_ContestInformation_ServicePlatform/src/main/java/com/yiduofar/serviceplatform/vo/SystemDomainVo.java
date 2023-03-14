package com.yiduofar.serviceplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 全平台管理Vo类
 * @author HuiBBao
 * @create 2023/3/10 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemDomainVo {

    /**
     * id
     */
    private Integer id;

    /**
     * name 名称
     */
    private String name;

    /**
     * intro
     */
    private String intro;

    /**
     * clazzType 哪类实体
     */
    private String clazzType;

    /**
     * actionType 操作类型
     */
    private String actionType;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * userStatus 用户状态
     */
    private Integer userStatus;

    /**
     * toArticleId 评论的帖子id
     */
    private Integer toArticleId;

    /**
     * 构造
     * @param id
     * @param name
     * @param intro
     * @param clazzType
     */
    public SystemDomainVo(Integer id, String name, String intro, String clazzType) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.clazzType = clazzType;
    }
}
