package org.example.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.example.dto.SettlementAgentDto;
import org.example.entity.settlement_product.SettlementProduct;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.vo.SettlementAgentListRequest;

@Mapper
public interface SettlementAgentMapper extends BaseMapper<SettlementAgent> {
//    List<SettlementAgentDto> selectByIdList(@Param("idList") List<String> idList);

    List<SettlementAgentDto> findList( SettlementAgentListRequest param);
}
