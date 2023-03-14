package com.yiduofar.serviceplatform.vo;

/**
 * @author HuiBBao
 * @create 2023/2/7 14:12
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String message;
    /**
     * 查询到的结果数据，
     */
    private T data;

    /**
     * 成功返回结果
     * @return
     */
    public static ResponseResult success() {
        return new ResponseResult(ResponseResultEnum.SUCCESS.getCode(), ResponseResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     * @return
     */
    public static ResponseResult success(Object o) {
        return new ResponseResult(ResponseResultEnum.SUCCESS.getCode(), ResponseResultEnum.SUCCESS.getMessage(), o);
    }

    /**
     * 失败返回结果
     * @param respBeanEnum
     * @return
     */
    public static ResponseResult error(ResponseResultEnum respBeanEnum) {
        return new ResponseResult(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param respBeanEnum
     * @return
     */
    public static ResponseResult error(ResponseResultEnum respBeanEnum, Object o) {
        return new ResponseResult(respBeanEnum.getCode(), respBeanEnum.getMessage(), o);
    }


    /**
     * 失败返回结果
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult error(int code, String message) {
        return new ResponseResult(code, message, null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult error(int code, String message, Object o) {
        return new ResponseResult(code, message, o);
    }

}