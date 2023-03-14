package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    @NotNull
    private String userName;

    /**
     * 头像
     */
    private String userHead;

    /**
     * 性别，0女，1男
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String intro;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份，0学生，1教师，2管理员
     */
    private Integer identity;

    /**
     * 学号或工号（例如，学号：201915270205，工号：201901）
     */

    private String num;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    /**
     * 账号状态 0注销 1正常 2封禁
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}