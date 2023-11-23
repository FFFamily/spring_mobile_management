package org.example.module;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.copy_mapper.SettlementProductCopyModule;
import org.example.core.policy.PolicyDto;
import org.example.core.policy.PromotionItemDto;
import org.example.entity.CommonException;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.SettlementProduct;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.SaleTypeEnum;
import org.example.mapper.SettlementProductMapper;
import org.example.module.process.Promotion;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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



    @PostConstruct
    public void init(){
        staticSettlementProductMapper = this.settlementProductMapper;
    }
    /**
     * 添加应收
     */
    public  void addReceivableSettlementByPolicy(PolicyDto policyDto){
        int saleType = policyDto.getSaleType();
        SaleTypeEnum saleTypeEnum = SaleTypeEnum.of(saleType);
        switch (saleTypeEnum){
            case ONLINE -> online(policyDto);
            case OFFLINE -> offline();
            default -> throw new CommonException("【应收应付】不支持的出单类型");
        }
    }

    /**
     * 线上
     */
    private  void online(PolicyDto policyDto){
        String key = "income_record_add_" + policyDto.getAccountId();
        synchronized (key){
            // 邀请奖励 和 活动奖励 线上出单不会有应收
            log.info("【Online】添加保单对应的系统出单类型数据");
            promotion.AddReceivableByPromotion(policyDto, FinanceRecordOriginTypeEnum.SYSTEM);
        }
    }

    /**
     * 线下
     */
    private static void offline(){

    }


    public static SettlementProduct getSettlementProduct(PolicyDto policyDto, PromotionItemDto promotionItemDto) {
        if (promotionItemDto.getSettlementAgents() != null && !promotionItemDto.getSettlementAgents().isEmpty()) {
            List<SettlementProduct.ProductSettlementAgent> settlementAgents =
                    promotionItemDto.getSettlementAgents().stream().map(SettlementProductCopyModule.INSTANCE::convert).collect(Collectors.toList());
            SettlementProduct settlementProduct = new SettlementProduct();
            settlementProduct.setSettlementAgents(settlementAgents);
            return settlementProduct;
        } else {
            // 查询结算产品
            return staticSettlementProductMapper
                    .selectOne(new LambdaQueryWrapper<SettlementProduct>()
                            .eq(SettlementProduct::getInsuranceId, policyDto.getInsuranceId()));
        }
    }

    public static ReceivableSettlement  createReceivableSettlement(){
        ReceivableSettlement receivableSettlement = new ReceivableSettlement();
        return receivableSettlement;
    }

}
