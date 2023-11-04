package org.example.entity;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private int code;
    private T data;
    private static final int SUCCESS_CODE = 200;

    /**
     * 成功方法
     * @param data
     * @return
     * @param <T>
     */
    public static <T> CommonResponse success(T data){
        CommonResponse<T> response = new CommonResponse<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }
}
