package org.example.entity.settlement_product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementProduct extends CommonEntity {
    // 险种Id
    private String insuranceId;
    // 开票项目Id
    private String billId;
    // 机构
    private String orgId;
}
