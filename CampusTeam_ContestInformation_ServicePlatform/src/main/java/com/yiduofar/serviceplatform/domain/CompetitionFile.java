package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName competition_file
 */
@TableName(value ="competition_file")
@Data
public class CompetitionFile implements Serializable {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer competitionFileId;

    /**
     * 竞赛id
     */
    private Integer competitionId;

    /**
     * 文件名
     */
    private String competitionFileName;

    /**
     * 文件路径
     */
    private String competitionFileUrl;

    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date competitionFileAnnounceTime;

    /**
     * 文件类型，0竞赛附件，1竞赛成绩，2报名竞赛上传资料
     */
    private Integer competitionFileType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}