package org.example.module.process;

import org.example.core.policy.CommissionRate;
import org.example.core.policy.Policy;
import org.example.enums.FinanceRecordOriginTypeEnum;

import java.util.*;

public class Promotion {
    /**
     *
     * @param policy 保单
     * @param type 应付来源类型
     */
    public static void AddReceivableByPromotion(Policy policy, FinanceRecordOriginTypeEnum type){
        List<CommissionRate> commissionRates = policy.getQuotePlan().getCommissionRates();
        for (ListIterator<CommissionRate> it = commissionRates.listIterator(); it.hasNext(); ) {
            // 期数
            int periodIndex = it.previousIndex() + 1;
            CommissionRate commissionRate = it.next();
            HashMap<String, String> promotion = Optional.ofNullable(commissionRate.getPromotion()).orElse(new HashMap<>());
            for (Map.Entry<String, String> item : promotion.entrySet()) {
                // 分项保费id
                String interfaceFieldId = item.getKey();
                // TODO 分项保费名称
                String interfaceFieldName = "TODO";
            }
        }
    }
}
