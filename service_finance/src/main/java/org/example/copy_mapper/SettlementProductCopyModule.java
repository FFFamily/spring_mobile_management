package org.example.copy_mapper;

import org.example.entity.SettlementProduct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SettlementProductCopyModule {

    SettlementProductCopyModule INSTANCE = Mappers.getMapper( SettlementProductCopyModule.class);

    SettlementProduct.ProductSettlementAgent convert(Object user);
}
