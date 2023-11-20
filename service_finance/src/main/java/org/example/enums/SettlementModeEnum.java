package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SettlementModeEnum {
    TO_PUBLIC(0,"对公"),
    TO_PRIVATE_ORG(1,"对私团队长"),
    TO_PRIVATE_ACCOUNT(2,"对私业务员")
    ;
    private final Integer code;
    private final String title;
}
