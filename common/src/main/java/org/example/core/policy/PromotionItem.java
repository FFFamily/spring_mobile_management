package org.example.core.policy;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PromotionItem {
    // 上游手续费
    private BigDecimal insuranceCompanyToSystem;
    // 保费
    private Long premium;
    /**
     * 结案主体配置
     * TODO 这个主要服务于线下录单，我应该暂时不会有线下录单的内容
     */
    private List<Object> settlementAgents;


}
