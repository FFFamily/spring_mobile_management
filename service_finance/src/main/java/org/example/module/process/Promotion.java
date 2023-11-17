package org.example.module.process;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.CommissionRate;
import org.example.core.policy.Policy;
import org.example.core.policy.PromotionItem;
import org.example.entity.CommonEntity;
import org.example.entity.ReceivableSettlement;
import org.example.entity.SettlementAgent;
import org.example.entity.SettlementProduct;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.mapper.ReceivableSettlementMapper;
import org.example.module.HistoryModule;
import org.example.module.ReceivableSettlementModule;
import org.example.module.SettlementAgentModule;
import org.example.module.SettlementModule;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
@Slf4j
public class Promotion {
    @Resource
    private ReceivableSettlementMapper receivableSettlementMapper;
    @Resource
    private HistoryModule historyModule;

    @Resource
    private SettlementAgentModule settlementAgentModule;

    /**
     *
     * @param policy 保单
     * @param type 应付来源类型
     */
    public void AddReceivableByPromotion(Policy policy, FinanceRecordOriginTypeEnum type){
        List<CommissionRate> commissionRates = policy.getQuotePlan().getCommissionRates();
        for (ListIterator<CommissionRate> it = commissionRates.listIterator(); it.hasNext(); ) {
            // 期数
            int periodIndex = it.previousIndex() + 1;
            CommissionRate commissionRate = it.next();
            HashMap<String, PromotionItem> promotion = Optional.ofNullable(commissionRate.getPromotion()).orElse(new HashMap<>());
            for (Map.Entry<String, PromotionItem> item : promotion.entrySet()) {
                // 分项保费id
                String interfaceFieldId = item.getKey();
                // TODO 分项保费名称
                String interfaceFieldName = "TODO";
                PromotionItem value = item.getValue();
                // 上游手续费
                BigDecimal insuranceCompanyToSystem = value.getInsuranceCompanyToSystem();
                Long premium = value.getPremium();
                if (SettlementModule.checkPremium(premium)){
                    return;
                }
                // 是否已经过了续期时间，一般新单不会，但是线下单录入可能会（目前没有续期）
                SettlementProduct settlementProduct = ReceivableSettlementModule.getSettlementProduct(policy, value);
                List<ReceivableSettlement> receivableSettlementList = new ArrayList<>();
                if (settlementProduct != null){
                    // 查询对应的结算主体信息
                    HashMap<String, SettlementAgent> agentHashMap =
                            settlementAgentModule.getAgentMapById(settlementProduct.getSettlementAgents().stream().map(CommonEntity::getId).toList());
                    BigDecimal sum = BigDecimal.ZERO;
                    for (int i = 0; i < settlementProduct.getSettlementAgents().size(); i++) {
                        SettlementProduct.ProductSettlementAgent settlementAgent = settlementProduct.getSettlementAgents().get(i);
                        ReceivableSettlement receivableSettlement = ReceivableSettlementModule.createReceivableSettlement();
                        if (i == settlementProduct.getSettlementAgents().size() - 1){
                            // 最后一个拆分需要用减法，以达到 100%的比例
                            receivableSettlement.setFeeRate(insuranceCompanyToSystem.subtract(sum));
                        }else {
                            BigDecimal feeRate = insuranceCompanyToSystem
                                    .multiply(SettlementModule.raleToNum(settlementAgent.getRate())) // 每个结算主体占一定比例的上游手续费
                                    .setScale(2, RoundingMode.HALF_UP);
                            receivableSettlement.setFeeRate(feeRate);
                            sum = sum.add(feeRate);
                        }
                        settlementAgentModule.setAgent(agentHashMap.get(settlementAgent.getId()),receivableSettlement);
                        // 进行计算
                        ReceivableSettlementModule.getAndSetAllResult(receivableSettlement);
                        receivableSettlementList.add(receivableSettlement);
                        log.info("【Promotion】准备添加应收记录：{}",receivableSettlement);
                    }
                    receivableSettlementMapper.insetReceivableSettlementList(receivableSettlementList);
                }else {
                    // 说明没有配置结算产品，只会生成一条结算记录
                    log.info("该险种：{} 没有配置结算主体",policy.getInsuranceId());
                    ReceivableSettlement receivableSettlement = ReceivableSettlementModule.createReceivableSettlement();
                    // 进行计算
                    ReceivableSettlementModule.getAndSetAllResult(receivableSettlement);
                    receivableSettlementMapper.insert(receivableSettlement);
                    // 构建历史记录
                    historyModule.initHistory(Collections.singletonList(receivableSettlement), policy);
                }
            }
        }
    }
}
