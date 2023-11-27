package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.entity.CommonException;
import org.example.entity.PaySettlement;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.PolicyTypeEnum;
import org.example.enums.SettlementModeEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 应付工具类
 */
@Component
@Slf4j
public class PaySettlementModule extends SettlementModule {

    public static PaySettlement createPaySettlement(
            PolicyDto policyDto,
            Object endorsementDto, // TODO 批单
            int periodIndex,
            String interfaceFieldId,
            String interfaceFieldName,
            Long premium,
            Integer finalSettlementSettlementMode,
            BigDecimal payRate, boolean reachRenewal, Object o1,
            FinanceRecordOriginTypeEnum financeRecordOriginTypeEnum) {
        PaySettlement paySettlement = new PaySettlement();
        paySettlement.setPeriodIndex(periodIndex);
        paySettlement.setPremium(premium);
        paySettlement.setCreatedScene(financeRecordOriginTypeEnum.getCode());
        paySettlement.setPayRate(payRate);
        paySettlement.setIncludeTaxPayMoney(CalculateModule.getPaySettlementResult(premium, payRate));
        if (endorsementDto != null) {
            paySettlement.setPolicyType(PolicyTypeEnum.ENDORSEMENT.getCode());
        } else {
            paySettlement.setPolicyType(PolicyTypeEnum.POLICY.getCode());
            paySettlement.setPolicyId(policyDto.getId());
            paySettlement.setPolicyNo(policyDto.getNo());
        }
        return new PaySettlement();
    }

    public static BigDecimal calculate(PaySettlement paySettlement) {
        return BigDecimal.valueOf(paySettlement.getPremium()).multiply(raleToNum(paySettlement.getPayRate()));
    }

    /**
     * 线下
     */
    private static void offline() {

    }

    /**
     * 计算其他信息：收入管理相关
     *
     * @param isCertificated           是否认证
     * @param premiumMoney             保费
     * @param orgToAgentRate           机构对业务员的佣金比例
     * @param superiorToAgentOrgRate   上层拥挤比例
     * @param finalSettlementToOrgRate 最终结算比例
     * @return 对应比例的金额
     */
    public static BigDecimal[] calculateOtherIncomeInfo(boolean isCertificated, Long premiumMoney, BigDecimal orgToAgentRate, BigDecimal superiorToAgentOrgRate, BigDecimal finalSettlementToOrgRate) {
        BigDecimal premium = BigDecimal.valueOf(premiumMoney);
        var promotionMoney = isCertificated ? premium.multiply(orgToAgentRate)
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        var orgPromotionMoney = premium.multiply(superiorToAgentOrgRate)
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        var finalSettlementOrgPromotionMoney = premium.multiply(finalSettlementToOrgRate)
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        return new BigDecimal[]{promotionMoney, orgPromotionMoney, finalSettlementOrgPromotionMoney};
    }

    /**
     * 获取应付佣金率
     *
     * @param finalSettlementSettlementMode 结算类型
     * @param finalSettlementToOrg          最终结算金额
     * @param orgToAgent                    机构下发给业务员金额
     * @return 应付佣金率
     */
    public static BigDecimal getPayRate(int finalSettlementSettlementMode, BigDecimal finalSettlementToOrg, BigDecimal orgToAgent) {
        BigDecimal payRate;
        if (SettlementModeEnum.TO_PUBLIC.getCode().equals(finalSettlementSettlementMode) ||
                SettlementModeEnum.TO_PRIVATE_ORG.getCode().equals(finalSettlementSettlementMode)
        ) {
            payRate = finalSettlementToOrg;
        } else if (SettlementModeEnum.TO_PRIVATE_ACCOUNT.getCode().equals(finalSettlementSettlementMode)) {
            payRate = orgToAgent;
        } else {
            throw new CommonException("no support finalSettlementSettlementMode " + finalSettlementSettlementMode);
        }
        return payRate;
    }

}
