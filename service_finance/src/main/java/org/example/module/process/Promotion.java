package org.example.module.process;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.core.policy.PromotionItemDto;
import org.example.dto.SettlementAgentDto;
import org.example.dto.SettlementProductDto;
import org.example.entity.CommonEntity;
import org.example.entity.PaySettlement;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.settlement_product.ProductSettlementAgent;
import org.example.entity.settlement_product.SettlementProduct;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.mapper.PaySettlementMapper;
import org.example.mapper.ReceivableSettlementMapper;
import org.example.module.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Component
public class Promotion {
    @Resource
    private ReceivableSettlementMapper receivableSettlementMapper;
    @Resource
    private ReceivableSettlementModule receivableSettlementModule;
    @Resource
    private PaySettlementMapper paySettlementMapper;
    @Resource
    private HistoryModule historyModule;
    @Resource
    private SettlementAgentModule settlementAgentModule;
    @Resource
    private SettlementModule settlementModule;

    /**
     * @param policyDto 保单
     * @param type      应付来源类型
     */
    public void AddReceivableByPromotion(PolicyDto policyDto, FinanceRecordOriginTypeEnum type) {
        List<PromotionItemDto> commissionRates = policyDto.getPromotions();
        for (ListIterator<PromotionItemDto> it = commissionRates.listIterator(); it.hasNext(); ) {
            // 期数
            int periodIndex = it.previousIndex() + 1;
            PromotionItemDto item = it.next();
            // 分项保费id
            String interfaceFieldId = item.getId();
            // TODO 分项保费名称
            String interfaceFieldName = item.getName();
            // 上游手续费
            BigDecimal insuranceCompanyToSystem = item.getInsuranceCompanyToSystem();
            Long premium = item.getPremium();
            if (SettlementModule.checkPremium(premium)) {
                return;
            }
            // 是否已经过了续期时间，一般新单不会，但是线下单录入可能会（目前没有续期）
            SettlementProductDto settlementProduct = receivableSettlementModule.getSettlementProduct(policyDto, item);
            List<ReceivableSettlement> receivableSettlementList = new ArrayList<>();
            if (settlementProduct != null) {
                // 查询对应的结算主体信息
                HashMap<String, SettlementAgentDto> agentHashMap =
                        settlementAgentModule.getAgentMapById(settlementProduct.getSettlementAgents().stream().map(CommonEntity::getId).toList());
                BigDecimal sum = BigDecimal.ZERO;
                for (int i = 0; i < settlementProduct.getSettlementAgents().size(); i++) {
                    ProductSettlementAgent settlementAgent = settlementProduct.getSettlementAgents().get(i);
                    ReceivableSettlement receivableSettlement =
                            ReceivableSettlementModule.createReceivableSettlement(
                                    policyDto,
                                    null,
                                    periodIndex,
                                    interfaceFieldId,
                                    interfaceFieldName,
                                    premium,
                                    type
                            );
                    if (i == settlementProduct.getSettlementAgents().size() - 1) {
                        // 最后一个拆分需要用减法，以达到 100%的比例
                        receivableSettlement.setFeeRate(insuranceCompanyToSystem.subtract(sum));
                    } else {
                        BigDecimal feeRate = insuranceCompanyToSystem
                                .multiply(SettlementModule.raleToNum(settlementAgent.getRate())) // 每个结算主体占一定比例的上游手续费
                                .setScale(2, RoundingMode.HALF_UP);
                        receivableSettlement.setFeeRate(feeRate);
                        sum = sum.add(feeRate);
                    }
                    settlementAgentModule.setAgent(agentHashMap.get(settlementAgent.getId()), receivableSettlement);
                    // 进行计算
                    CalculateModule.getAndSetAllResult(receivableSettlement);
                    receivableSettlementList.add(receivableSettlement);
                    log.info("【Promotion】准备添加应收记录：{}", receivableSettlement);
                }
                receivableSettlementMapper.insetReceivableSettlementList(receivableSettlementList);
                // 构建历史记录
                historyModule.initHistory(receivableSettlementList, policyDto);
            } else {
                // 说明没有配置结算产品，只会生成一条结算记录
                log.info("该险种：{} 没有配置结算主体", policyDto.getInsuranceId());
                ReceivableSettlement receivableSettlement = ReceivableSettlementModule.createReceivableSettlement(policyDto,null, periodIndex, interfaceFieldId, interfaceFieldName, premium, type);
                // 进行计算
                CalculateModule.getAndSetAllResult(receivableSettlement);
                log.info("【Promotion】准备添加应收记录：{}", receivableSettlement);
                receivableSettlementMapper.insert(receivableSettlement);
                // 构建历史记录
                historyModule.initHistory(Collections.singletonList(receivableSettlement), policyDto);
            }
        }
    }

    public void AddPaySettlementByPromotion(PolicyDto policyDto, FinanceRecordOriginTypeEnum financeRecordOriginTypeEnum, Integer finalSettlementSettlementMode) {
        boolean isCertificated = settlementModule.checkIsCertificated(policyDto.getAccountId());
        if (isCertificated) {
            log.info("该用户 {} 没有认证，该单的推广费为0", policyDto.getAccountId());
        }
        List<PromotionItemDto> promotions = policyDto.getPromotions();
        for (ListIterator<PromotionItemDto> it = promotions.listIterator(); it.hasNext(); ) {
            // 期数
            int periodIndex = it.previousIndex() + 1;
            PromotionItemDto item = it.next();
            String interfaceFieldId = item.getId();
            // TODO 分项保费名称
            String interfaceFieldName = "TODO";
            // 保费
            Long premium = item.getPremium();
            if (SettlementModule.checkPremium(premium)) {
                return;
            }
            BigDecimal[] calculated = PaySettlementModule.calculateOtherIncomeInfo(
                    isCertificated,
                    premium,
                    item.getOrgToAccount(),
                    item.getSuperiorToAccountOrg(),
                    item.getFinalSettlementToOrg()
            );
            BigDecimal promotionMoney = calculated[0];
            BigDecimal orgPromotionMoney = calculated[1];
            BigDecimal finalSettlementOrgPromotionMoney = calculated[2];
            // 是否已经过了续期时间，一般新单不会，但是线下单录入可能会
            var reachRenewal = SettlementModule.checkReachRenewal(policyDto, periodIndex);
            BigDecimal payRate = PaySettlementModule.getPayRate(finalSettlementSettlementMode, item.getFinalSettlementToOrg(), item.getOrgToAccount());
            PaySettlement paySettlement = PaySettlementModule.createPaySettlement(
                    policyDto,
                    null,
                    periodIndex,
                    interfaceFieldId,
                    interfaceFieldName,
                    premium,
                    finalSettlementSettlementMode,
                    payRate,
                    reachRenewal,
                    null,
                    financeRecordOriginTypeEnum
            );
            paySettlement.setIncludeTaxPayMoney(PaySettlementModule.calculate(paySettlement).longValue());
            paySettlementMapper.insert(paySettlement);
            log.info("【Promotion】准备添加应付记录 {}", paySettlement);
            // 创建历史记录
            HistoryModule.initPaymentSettlementHistory(paySettlement, policyDto);
        }

    }
}
