package org.example.config;

import org.example.entity.CommonException;
import org.example.entity.CommonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public CommonResponse<String> handleBusinessException(CommonException e) {
        return CommonResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public CommonResponse<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return CommonResponse.error("系统执行出现错误："+e.getMessage());
    }
}
