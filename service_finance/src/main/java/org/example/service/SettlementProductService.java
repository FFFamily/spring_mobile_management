package org.example.service;

import org.example.dto.SettlementProductDto;
import org.example.entity.CommonResponse;
import org.example.entity.settlement_product.ProductSettlementAgent;
import org.example.entity.settlement_product.SettlementProduct;
import org.example.mapper.SettlementProductMapper;
import org.example.vo.SettlementProductListRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SettlementProductService {
    @Resource
    public SettlementProductMapper settlementProductMapper;

    /*
        添加结算产品
     */
    public void save(SettlementProductDto settlementProductDto) {
        SettlementProduct settlementProduct = new SettlementProduct();
        BeanUtils.copyProperties(settlementProductDto,settlementProduct);
        settlementProductMapper.insert(settlementProduct);
        List<ProductSettlementAgent> settlementAgents = settlementProductDto.getSettlementAgents();
        settlementAgents.forEach(item -> item.setProductId(settlementProduct.getId()));
        settlementProductMapper.insertAgents(settlementAgents);
    }

    public List<SettlementProductDto> list(SettlementProductListRequest request) {
        return settlementProductMapper.findList(request);
    }
}
