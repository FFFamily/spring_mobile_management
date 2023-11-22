package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementAgent extends CommonEntity{
    // 结算主体名称
    private String name;
    // 结算主体类型
    private Integer type;
    // 机构
    private String orgId;
    // 内容
    private List<SettlementAgentInfo> info;

    @Data
    public static class SettlementAgentInfo{
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
}
