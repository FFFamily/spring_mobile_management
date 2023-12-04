package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.entity.CommonException;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.mapper.SettlementAgentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SettlementAgentService {
    @Resource
    private SettlementAgentMapper settlementAgentMapper;
    /**
     * 创建结算主体
     */
    public void save(SettlementAgent settlementAgent) {
        SettlementAgent agent = settlementAgentMapper.selectOne(
                new LambdaQueryWrapper<SettlementAgent>().eq(SettlementAgent::getName, settlementAgent.getName())
        );
        if (agent != null){
            throw new CommonException("已存在相同名称的结算主体");
        }

    }
}
