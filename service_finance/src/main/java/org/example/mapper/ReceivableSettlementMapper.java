package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.receivable_settlement.ReceivableSettlement;

import java.util.List;

@Mapper
public interface ReceivableSettlementMapper extends BaseMapper<ReceivableSettlement> {
    /**
     * 批量添加应收记录
     */
    void insetReceivableSettlementList(List<ReceivableSettlement> receivableSettlementList);
}
