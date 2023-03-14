package com.yiduofar.serviceplatform.exception;

import com.yiduofar.serviceplatform.vo.ResponseResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{

    private ResponseResultEnum responseResultEnum;

}