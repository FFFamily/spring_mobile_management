package org.example.dto;

import org.example.entity.settlement_product.ProductSettlementAgent;

import java.util.List;

public class SettlementProductDto {
    // 险种Id
    private String insuranceId;
    // 开票项目Id
    private String billId;
    // 结算主体
    private List<ProductSettlementAgent> settlementAgents;
    // 机构
    private String orgId;
}
