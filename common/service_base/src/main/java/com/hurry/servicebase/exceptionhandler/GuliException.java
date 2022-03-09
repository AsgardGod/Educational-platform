package com.hurry.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/2/20
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
@AllArgsConstructor     //有参构造
@NoArgsConstructor      //无参构造
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;


}
