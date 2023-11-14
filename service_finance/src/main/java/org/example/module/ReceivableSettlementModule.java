package org.example.module;

import org.example.core.Policy;
import org.example.enums.SaleTypeEnum;

/**
 * 应收工具类
 */
public class ReceivableSettlementModule extends SettlementModule {
    /**
     * 添加应收
     */
    public static void addReceivableSettlementByPolicy(Policy policy){
        int saleType;
        SaleTypeEnum saleTypeEnum = SaleTypeEnum.of(saleType);
        switch (saleTypeEnum){
            case ONLINE -> online();
        }
    }

    /**
     * 线上
     */
    private static void online(){

    }

    /**
     * 线下
     */
    private static void offline(){

    }

}
