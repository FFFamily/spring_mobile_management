package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.settlement_agent.SettlementAgentInfo;

import java.util.List;

@Mapper
public interface SettlementAgentInfoMapper extends BaseMapper<SettlementAgentInfo> {
    void insertList(List<SettlementAgentInfo> list);
}
