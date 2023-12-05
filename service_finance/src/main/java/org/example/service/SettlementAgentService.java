package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.dto.SettlementAgentDto;
import org.example.entity.CommonException;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.entity.settlement_agent.SettlementAgentInfo;
import org.example.mapper.SettlementAgentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SettlementAgentService {
    @Resource
    private SettlementAgentMapper settlementAgentMapper;
    /**
     * 创建结算主体
     */
    public void save(SettlementAgentDto settlementAgent) {
        SettlementAgent oldAgent = settlementAgentMapper.selectOne(
                new LambdaQueryWrapper<SettlementAgent>().eq(SettlementAgent::getName, settlementAgent.getName())
        );
        if (oldAgent != null){
            throw new CommonException("已存在相同名称的结算主体");
        }
        SettlementAgent newAgent = new SettlementAgent();
        newAgent.setOrgId(settlementAgent.getOrgId());
        newAgent.setName(settlementAgent.getName());
        newAgent.setType(settlementAgent.getType());
        settlementAgentMapper.insert(newAgent);
        List<SettlementAgentInfo> info = settlementAgent.getInfo();


    }
}
