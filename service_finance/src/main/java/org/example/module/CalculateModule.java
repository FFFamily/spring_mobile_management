package org.example.module;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.receivable_settlement.ReceivableSettlementAgent;
import org.example.enums.SettlementAgentTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
@Slf4j
public class CalculateModule {

    public static Long getPaySettlementResult(Long premium,BigDecimal payRate){
        return SettlementModule
                .raleToNum(payRate)
                .multiply(BigDecimal.valueOf(premium))
                .setScale(0,RoundingMode.HALF_UP)
                .longValue();
    }

    public static void getAndSetAllResult(ReceivableSettlement receivableSettlement) {
        CalculateResult calcResult = getAllResult(receivableSettlement);
        log.info("结算 ,{}", calcResult);
        receivableSettlement.setFee(calcResult.getFee()); // 手续费
        receivableSettlement.setTaxMoney(calcResult.getTaxMoney());  // 税金
        receivableSettlement.setFeeMoney(calcResult.getFeeMoney()); // 手续费税金
        receivableSettlement.setExcludeTaxFee(calcResult.getExcludeTaxFee()); // 不含税手续费
        receivableSettlement.setExcludeTaxPremium(calcResult.getExcludeTaxPremium()); // 不含税保费
        receivableSettlement.setIncludeTaxSettleMoney(calcResult.getIncludeTaxSettleMoney()); // 应收含税金额
    }

    /**
     * 计算所有金额
     */
    public static CalculateResult getAllResult(ReceivableSettlement receivableSettlement) {
        Boolean isIncludeTax = receivableSettlement.getIsIncludeTax();
        BigDecimal feeRate = receivableSettlement.getFeeRate();
        BigDecimal taxRate = receivableSettlement.getTaxRate();
        BigDecimal agentTaxRate = receivableSettlement.getAgentTaxRate();
        // 含税保费
        Long premium = receivableSettlement.getPremium();
        // 不含税保费
        Long excludeTaxPremium = getExcludeTaxPremium(premium, taxRate);
        // 税金
        Long taxMoney = getTaxMoney(premium, excludeTaxPremium);
        // 手续费
        Long fee = getFee(isIncludeTax, premium, feeRate, excludeTaxPremium);
        // 不含税手续费
        Long excludeTaxFee = receivableSettlement.getAgentId() != null ? getExcludeTaxFee(fee, agentTaxRate) : null;
        // 手续费税金
        Long feeMoney = getFeeMoney(fee, excludeTaxFee);
        return getAllResult(receivableSettlement, premium, taxMoney, excludeTaxPremium, fee, excludeTaxFee, feeMoney);
    }

    public static CalculateResult getAllResult(ReceivableSettlement receivableSettlement, Long premium, Long taxMoney, Long excludeTaxPremium, Long fee, Long excludeTaxFee, Long feeMoney) {
        CalculateResult calculateResult = new CalculateResult();
        BigDecimal agentTaxRate = receivableSettlement.getAgentTaxRate();
        Boolean isIncludeTax = receivableSettlement.getIsIncludeTax();
        BigDecimal feeRate = receivableSettlement.getFeeRate();
        BigDecimal taxRate = receivableSettlement.getTaxRate();
        // 含税保费
        premium = premium == null ? receivableSettlement.getPremium() : premium;
        // 不含税保费
        excludeTaxPremium = excludeTaxPremium == null ? getExcludeTaxPremium(premium, taxRate) : excludeTaxPremium;
        // 税金
        taxMoney = taxMoney == null ? getTaxMoney(premium, excludeTaxPremium) : taxMoney;
        // 手续费
        fee = fee == null ? getFee(isIncludeTax, premium, feeRate, excludeTaxPremium) : fee;
        if (!check(receivableSettlement.getAgentId())) {
            // 不含税手续费
            excludeTaxFee = excludeTaxFee == null ? getExcludeTaxFee(fee, agentTaxRate) : excludeTaxFee;
        } else {
            excludeTaxFee = null;
        }

        if (excludeTaxFee != null) {
            calculateResult.setExcludeTaxFee(excludeTaxFee);
        }
        // 手续费税金
        feeMoney = feeMoney == null ? getFeeMoney(fee, excludeTaxFee) : feeMoney;
        if (fee != null) {
            calculateResult.setFee(fee);
        }
        if (feeMoney != null) {
            calculateResult.setFeeMoney(feeMoney);
        }

        if (taxMoney != null) {
            calculateResult.setTaxMoney(taxMoney);
        }
        if (excludeTaxPremium != null) {
            calculateResult.setExcludeTaxPremium(excludeTaxPremium);
        }
        if (!check(receivableSettlement.getAgentId())) {
            // 最后结果
            calculateResult.setIncludeTaxSettleMoney(
                    calculate(
                            premium,
                            excludeTaxPremium,
                            fee,
                            excludeTaxFee,
                            receivableSettlement.getAgentType(),
                            isIncludeTax,
                            receivableSettlement.getAgentIsIncludeTax(),
                            feeRate,
                            receivableSettlement.getAgentFeeRate()
                    )
            );
        }
        return calculateResult;
    }

    public static Long calculate(Long premium, Long excludeTaxPremium, Long fee, Long excludeTaxFee, Integer type, Boolean isIncludeTax, Boolean isAgentIncludeTax, BigDecimal settleFeeRate, BigDecimal agentFeeRate) {
        // 当 “保司含税结算” 时，税金 = 含税保费 * 保司税率
        // 当 “保司不含税结算” 时， 税金 = 0
        BigDecimal includeTaxSettleMoney;
        // 若结算主体为“保司结算”
        if (check(type, isIncludeTax)) return null;
        if (Objects.equals(type, SettlementAgentTypeEnum.DIRECT_SETTLE.getValue())) {
            if (check(premium, settleFeeRate, agentFeeRate, excludeTaxPremium)) return null;
            if (isIncludeTax) {
                // 若保司是否含税结算为“是”，则应收含税金额=保费*手续费率*结算费率
                includeTaxSettleMoney = SettlementModule.setScale(BigDecimal.valueOf(premium).multiply(SettlementModule.raleToNum(settleFeeRate)).multiply(SettlementModule.raleToNum(agentFeeRate)));
            } else {
                // 若保司是否含税为“否”，则应收含税金额=不含税保费*手续费率*结算费率
                includeTaxSettleMoney = SettlementModule.setScale(BigDecimal.valueOf(excludeTaxPremium).multiply(SettlementModule.raleToNum(settleFeeRate)).multiply(SettlementModule.raleToNum(agentFeeRate)));
            }
        } else {
            if (check(isAgentIncludeTax, fee, agentFeeRate, excludeTaxFee)) return null;
            // 若 “结算主体” 不是 “保司结算”
            if (isAgentIncludeTax) {
                // 结算主体是否含税结算为“是”，则应收含税金额=手续费(元 含税)*结算费率
                includeTaxSettleMoney = SettlementModule.setScale(BigDecimal.valueOf(fee).multiply(SettlementModule.raleToNum(agentFeeRate)));
            } else {
                // 结算主体是否含税结算为”否”，则应收含税金额=不含税手续费（元）*结算费率
                includeTaxSettleMoney = SettlementModule.setScale(BigDecimal.valueOf(excludeTaxFee).multiply(SettlementModule.raleToNum(agentFeeRate)));
            }
        }
        return includeTaxSettleMoney.longValue();
    }

    public static Long getExcludeTaxPremium(Long premium, BigDecimal taxRate) {
        if (check(premium, taxRate)) {
            return null;
        }
        return BigDecimal.valueOf(premium).divide(BigDecimal.valueOf(100).add(taxRate).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP), 0, RoundingMode.HALF_UP).longValue();
    }

    public static Long getTaxMoney(Long premium, Long excludeTaxPremium) {
        // 税金 = 含税保费 * 保司税率
        if (check(premium, excludeTaxPremium)) {
            return null;
        }
        return premium - excludeTaxPremium;
    }

    public static Long getFee(Boolean isIncludeTax, Long premium, BigDecimal feeRate, Long excludeTaxPremium) {
        // 当 “保司含税结算时” 手续费 = 含税保费 * 手续费率
        // 当 “保司不含税结算时” 手续费 = 不含税保费 * 手续费率
        if (check(isIncludeTax, premium, feeRate, excludeTaxPremium)) {
            return null;
        }
        return isIncludeTax ? SettlementModule.setScale(BigDecimal.valueOf(premium).multiply(SettlementModule.raleToNum(feeRate))).longValue() : SettlementModule.setScale(BigDecimal.valueOf(excludeTaxPremium)).multiply(SettlementModule.raleToNum(feeRate)).longValue();
    }

    public static Long getExcludeTaxFee(Long fee, BigDecimal taxRate) {
        if (check(fee, taxRate)) {
            return null;
        }
        return BigDecimal.valueOf(fee).divide(BigDecimal.valueOf(1).add(SettlementModule.raleToNum(taxRate)), 0, RoundingMode.HALF_UP).longValue();
    }

    public static Long getFeeMoney(Long fee, Long excludeTaxFee) {
        if (check(fee, excludeTaxFee)) {
            return null;
        }
        return fee - excludeTaxFee;
    }

    public static boolean check(Object... objects) {
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }


    @Data
    @ToString
    public static class CalculateResult {
        // 税金
        private Long taxMoney;
        // 不含税保费
        private Long excludeTaxPremium;
        // 手续费
        private Long fee;
        // 手续费税金
        private Long feeMoney;
        // 不含税手续费
        private Long excludeTaxFee;
        // 应收含税金额
        private Long includeTaxSettleMoney;
    }
}
