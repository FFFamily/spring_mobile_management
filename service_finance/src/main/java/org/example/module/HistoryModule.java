package org.example.module;

import org.example.core.policy.Policy;
import org.example.entity.PaySettlement;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryModule {
    public static void initPaymentSettlementHistory(PaySettlement paySettlement, Policy policy) {

    }

    /**
     * 首次构建应收的记录记录
     */
    public void initHistory(List<ReceivableSettlement> receivableSettlements, Policy policy) {

    }
}
