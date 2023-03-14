package com.yiduofar.serviceplatform.vo;

import com.yiduofar.serviceplatform.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author HuiBBao
 * @create 2023/2/7 16:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegVo {
    @NotNull
    @IsMobile
    public String phone;

    @NotNull
    @Length(min = 1)
    public String password;

    @NotNull
    public String userName;

    @NotNull
    public String college;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    public String num;

    @NotNull
    public String email;
}
