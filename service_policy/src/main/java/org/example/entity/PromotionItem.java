package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PromotionItem {
    // 分项保费Id
    private String id;
    // 保单id
    private String policyId;
    // 分项保费名称
    private String name;
    // 上游手续费
    private BigDecimal insuranceCompanyToSystem;
    // 保费
    // TODO 保费真的需要吗？
    private Long premium;
    // 机构给业务员 下发佣金
    private BigDecimal orgToAccount;
    // 机构给机构 上游手续费
    private BigDecimal superiorToAccountOrg;
    // 最总结算费率
    private BigDecimal finalSettlementToOrg;
    /**
     * 结案主体配置
     * TODO 这个主要服务于线下录单，我应该暂时不会有线下录单的内容
     */
    private List<Object> settlementAgents;


}
