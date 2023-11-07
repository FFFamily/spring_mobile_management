package org.example.entity;

/**
 * 通用异常错误
 */
public class CommonException extends RuntimeException{
    public CommonException(String message) {
        super(message);
    }
}
