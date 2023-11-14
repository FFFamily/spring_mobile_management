package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaleTypeEnum implements DefaultEnum<Integer> {
    // 线下
    OFFLINE(0),
    // 网销
    ONLINE(1),
    // 预约
    APPOINTMENT(2);
    private final Integer code;


    public static SaleTypeEnum of(Integer value){
        return DefaultEnum.of(SaleTypeEnum.class,value);
    }
}
