package org.example.entity.settlement_product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.SettlementAgent;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSettlementAgent extends SettlementAgent {
    private BigDecimal rate;
}
