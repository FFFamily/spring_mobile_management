package org.example.entity.settlement_agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = false)
@Data
public class SettlementAgentInfo extends CommonEntity {
    // 对应结算主体id
    private String agentId;
    // 结算费率
    private BigDecimal feeRate;
    // 结算税率
    private BigDecimal taxRate;
    // 结算主体是否含税结算
    private Boolean isIncludeTax;
    // 生效起期
    private Long from;
    // 生效止期
    private Long end;
    // 开票项目
    private String billId;
}
