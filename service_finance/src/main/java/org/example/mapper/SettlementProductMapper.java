package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.settlement_product.SettlementProduct;

@Mapper
public interface SettlementProductMapper extends BaseMapper<SettlementProduct> {

    SettlementProduct findAllByInsuranceId(String insuranceId);

}
