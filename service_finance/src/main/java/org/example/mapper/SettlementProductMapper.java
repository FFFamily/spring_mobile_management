package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.dto.SettlementProductDto;
import org.example.entity.CommonResponse;
import org.example.entity.settlement_product.ProductSettlementAgent;
import org.example.entity.settlement_product.SettlementProduct;
import org.example.vo.SettlementProductListRequest;

import java.util.List;

@Mapper
public interface SettlementProductMapper extends BaseMapper<SettlementProduct> {

    SettlementProductDto findAllByInsuranceId(String insuranceId);

    void insertAgents(List<ProductSettlementAgent> settlementAgents);

    List<SettlementProductDto> findList(SettlementProductListRequest request);
}
