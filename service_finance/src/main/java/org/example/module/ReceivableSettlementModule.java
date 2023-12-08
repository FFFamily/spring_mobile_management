package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.copy_mapper.SettlementProductCopyModule;
import org.example.core.policy.EndorsementDto;
import org.example.core.policy.PolicyDto;
import org.example.core.policy.PromotionItemDto;
import org.example.dto.SettlementProductDto;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.settlement_product.ProductSettlementAgent;
import org.example.entity.settlement_product.SettlementProduct;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.PolicyTypeEnum;
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

    public static ReceivableSettlement createReceivableSettlement(PolicyDto policyDto, EndorsementDto endorsement,
                                                                  int periodIndex, String interfaceFieldId,
                                                                  String interfaceFieldName, Long premium,
                                                                  FinanceRecordOriginTypeEnum type) {
        ReceivableSettlement receivableSettlement = new ReceivableSettlement();
        receivableSettlement.setPolicyId(policyDto.getId());
        receivableSettlement.setPolicyNo(policyDto.getNo());
        if (endorsement != null){
            receivableSettlement.setPolicyType(PolicyTypeEnum.ENDORSEMENT.getCode());
            receivableSettlement.setEndorsementNo(endorsement.getNo());
            receivableSettlement.setEndorsementId(endorsement.getId());
        }else {
            receivableSettlement.setPolicyType(PolicyTypeEnum.POLICY.getCode());
        }
        receivableSettlement.setPeriodIndex(periodIndex);
        receivableSettlement.setInterfaceFieldId(interfaceFieldId);
        receivableSettlement.setInterfaceFieldName(interfaceFieldName);
        receivableSettlement.setPremium(premium);
        receivableSettlement.setType(type.getCode());
        return receivableSettlement;
    }

    public SettlementProductDto getSettlementProduct(PolicyDto policyDto, PromotionItemDto promotionItemDto) {
        if (promotionItemDto.getSettlementAgents() != null && !promotionItemDto.getSettlementAgents().isEmpty()) {
            List<ProductSettlementAgent> settlementAgents =
                    promotionItemDto.getSettlementAgents().stream().map(SettlementProductCopyModule.INSTANCE::convert).collect(Collectors.toList());
            SettlementProductDto settlementProduct = new SettlementProductDto();
            settlementProduct.setSettlementAgents(settlementAgents);
            return settlementProduct;
        } else {
            // 查询结算产品
            return settlementProductMapper.findAllByInsuranceId(policyDto.getInsuranceId());
        }
    }
}
