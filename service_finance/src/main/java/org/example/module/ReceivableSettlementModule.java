package org.example.module;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.copy_mapper.SettlementProductCopyModule;
import org.example.core.policy.Policy;
import org.example.core.policy.PromotionItem;
import org.example.entity.CommonException;
import org.example.entity.ReceivableSettlement;
import org.example.entity.SettlementProduct;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.SaleTypeEnum;
import org.example.mapper.SettlementProductMapper;
import org.example.module.process.Activity;
import org.example.module.process.InviterAward;
import org.example.module.process.Promotion;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 应收工具类
 */
@Slf4j
@Component
public class ReceivableSettlementModule extends SettlementModule {

    private static SettlementProductMapper staticSettlementProductMapper;

    @Resource
    private SettlementProductMapper settlementProductMapper;
    @Resource
    private Promotion promotion;

    /**
     * 计算所有金额
     */
    public static void getAndSetAllResult(ReceivableSettlement receivableSettlement) {

    }

    @PostConstruct
    public void init(){
        staticSettlementProductMapper = this.settlementProductMapper;
    }
    /**
     * 添加应收
     */
    public  void addReceivableSettlementByPolicy(Policy policy){
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
    private  void online(Policy policy){
        String key = "income_record_add_" + policy.getAccountId();
        synchronized (key){
            log.info("【Online】添加保单对应的系统出单类型数据");
            promotion.AddReceivableByPromotion(policy, FinanceRecordOriginTypeEnum.SYSTEM);
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


    public static SettlementProduct getSettlementProduct(Policy policy, PromotionItem promotionItem) {
        if (promotionItem.getSettlementAgents() != null && !promotionItem.getSettlementAgents().isEmpty()) {
            List<SettlementProduct.ProductSettlementAgent> settlementAgents =
                    promotionItem.getSettlementAgents().stream().map(SettlementProductCopyModule.INSTANCE::convert).collect(Collectors.toList());
            SettlementProduct settlementProduct = new SettlementProduct();
            settlementProduct.setSettlementAgents(settlementAgents);
            return settlementProduct;
        } else {
            // 查询结算产品
            return staticSettlementProductMapper
                    .selectOne(new LambdaQueryWrapper<SettlementProduct>()
                            .eq(SettlementProduct::getInsuranceId, policy.getInsuranceId())
                    );
        }
    }

    public static ReceivableSettlement  createReceivableSettlement(){
        ReceivableSettlement receivableSettlement = new ReceivableSettlement();
        return receivableSettlement;
    }

}
