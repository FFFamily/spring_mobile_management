package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PolicyTypeEnum {
    POLICY(0,"保单"),
    ENDORSEMENT(1,"批单"),
    ;
    private final Integer code;
    private final String title;
}
