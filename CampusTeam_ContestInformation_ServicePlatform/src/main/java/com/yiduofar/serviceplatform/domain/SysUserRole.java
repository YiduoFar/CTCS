package com.yiduofar.serviceplatform.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author HuiBBao
 * @create 2023/2/12 1:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserRole {

    @TableId
    private Integer user_id;

    private Integer role_id;

}
