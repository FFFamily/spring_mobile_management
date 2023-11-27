package org.example.copy_mapper;

import org.example.entity.settlement_product.ProductSettlementAgent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementProductCopyModule {

    SettlementProductCopyModule INSTANCE = Mappers.getMapper(SettlementProductCopyModule.class);

    ProductSettlementAgent convert(Object user);
}
