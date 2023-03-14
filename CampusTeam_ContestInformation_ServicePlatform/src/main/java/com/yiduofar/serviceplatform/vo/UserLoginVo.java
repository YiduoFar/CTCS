package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginVo {

    @NotNull
    @IsMobile
    public String phone;

    @NotNull
    public String password;


}
