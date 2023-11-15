package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.Policy;
import org.example.entity.CommonException;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.SaleTypeEnum;
import org.example.module.process.Activity;
import org.example.module.process.InviterAward;
import org.example.module.process.Promotion;

/**
 * 应收工具类
 */
@Slf4j
public class ReceivableSettlementModule extends SettlementModule {
    /**
     * 添加应收
     */
    public static void addReceivableSettlementByPolicy(Policy policy){
        int saleType = policy.getSaleType();
        SaleTypeEnum saleTypeEnum = SaleTypeEnum.of(saleType);
        switch (saleTypeEnum){
            case ONLINE -> online(policy);
            case OFFLINE -> offline();
            default -> throw new CommonException("【应收应付】不支持的出单类型");
        }
    }

    /**
     * 线上
     */
    private static void online(Policy policy){
        String key = "income_record_add_" + policy.getAccountId();
        synchronized (key){
            log.info("【Online】添加保单对应的系统出单类型数据");
            Promotion.AddReceivableByPromotion(policy, FinanceRecordOriginTypeEnum.SYSTEM);
            log.info("【Online】添加保单对应的邀请奖励类型数据");
            InviterAward.AddReceivableByInviterAward(policy);
            log.info("【Online】添加保单对应的活动奖励类型数据");
            Activity.AddReceivableByActivity(policy);
        }
    }

    /**
     * 线下
     */
    private static void offline(){

    }

}
