package org.example.enums.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 保单类型枚举
 */
@AllArgsConstructor
@Getter
public enum PolicyTypeEnum {
    POLICY(0,"保单"),
    ENDORSEMENT(1,"批单");
    ;
    private final Integer code;
    private final String title;
}
