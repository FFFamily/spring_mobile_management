package org.example.entity;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private T data;
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 500;


    /**
     * 成功方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> CommonResponse<T> success(T data){
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }


    public static <T> CommonResponse<T> error(T data){
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(ERROR_CODE);
        response.setData(data);
        return response;
    }

    public static <T> CommonResponse<T> success(){
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(SUCCESS_CODE);
        return response;
    }
}
