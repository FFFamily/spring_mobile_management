package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceRecordOriginTypeEnum {
    SYSTEM(0, "系统出单"),
    OFFLINE(1, "线下录单"),
    INVITE(2, "邀请奖励"),
    ACTIVITY(3, "活动奖励"),
    RENEWAL(4, "续期"),
    SYSTEM_REBATE(5, "系统出单扣回"),
    OFFLINE_REBATE(6, "线下录单扣回"),
    INVITE_REBATE(7, "邀请奖励扣回"),
    ACTIVITY_REBATE(8, "活动奖励扣回"),
    ;
    private final Integer code;
    private final String name;
}
