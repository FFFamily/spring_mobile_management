package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.copy_mapper.SettlementProductCopyModule;
import org.example.core.policy.PolicyDto;
import org.example.core.policy.PromotionItemDto;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.settlement_product.ProductSettlementAgent;
import org.example.entity.settlement_product.SettlementProduct;
import org.example.mapper.SettlementProductMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应收工具类
 */
@Slf4j
@Component
public class ReceivableSettlementModule {

    @Resource
    private SettlementProductMapper settlementProductMapper;

    public static ReceivableSettlement createReceivableSettlement() {
        ReceivableSettlement receivableSettlement = new ReceivableSettlement();
        return receivableSettlement;
    }

    public SettlementProduct getSettlementProduct(PolicyDto policyDto, PromotionItemDto promotionItemDto) {
        if (promotionItemDto.getSettlementAgents() != null && !promotionItemDto.getSettlementAgents().isEmpty()) {
            List<ProductSettlementAgent> settlementAgents =
                    promotionItemDto.getSettlementAgents().stream().map(SettlementProductCopyModule.INSTANCE::convert).collect(Collectors.toList());
            SettlementProduct settlementProduct = new SettlementProduct();
            settlementProduct.setSettlementAgents(settlementAgents);
            return settlementProduct;
        } else {
            // 查询结算产品
            return settlementProductMapper.findAllByInsuranceId(policyDto.getInsuranceId());
        }
    }

    //    private static SettlementProductMapper staticSettlementProductMapper;
//    @PostConstruct
//    public void init() {
//        staticSettlementProductMapper = this.settlementProductMapper;
//    }


}
