package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettlementAgentTypeEnum {
    DIRECT_SETTLE(0), // 保司结算
    INDIRECT_SETTLE(1), // 经代结算
    PERSONAL_SETTLE(2),// 个人结算
    OTHER(-1) // 其他
    ;
    private final Integer value;
}
