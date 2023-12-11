package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.CommonEntity;
import org.example.entity.settlement_product.ProductSettlementAgent;

import java.util.List;
@Getter
@Setter
public class SettlementProductDto extends CommonEntity {
    // 险种Id
    private String insuranceId;
    // 开票项目Id
    private String billId;
    // 结算主体
        private List<ProductSettlementAgent> settlementAgents;
    // 机构
    private String orgId;
}
