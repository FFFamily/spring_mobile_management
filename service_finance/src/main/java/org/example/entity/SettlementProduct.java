package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.beans.BeanInfo;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementProduct extends CommonEntity{
    // 险种Id
    private String insuranceId;
    // 开票项目Id
    private String billId;
    // 结算主体
    private List<ProductSettlementAgent> settlementAgents;
    // 机构
    private String orgId;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ProductSettlementAgent extends SettlementAgent{
        private BigDecimal rate;
    }
}
