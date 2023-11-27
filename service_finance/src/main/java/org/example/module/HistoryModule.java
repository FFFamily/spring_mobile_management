package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.entity.PaySettlement;
import org.example.entity.SettlementHistory;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HistoryModule {
    public static void initPaymentSettlementHistory(PaySettlement paySettlement, PolicyDto policyDto) {
        log.info("【构建历史记录完成】");
    }

    /**
     * 首次构建应收的记录记录
     */
    public void initHistory(List<ReceivableSettlement> receivableSettlements, PolicyDto policyDto) {
        log.info("【构建历史记录完成】");
    }
}
