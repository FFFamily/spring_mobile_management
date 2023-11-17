package org.example.module;

import org.example.entity.ReceivableSettlement;
import org.example.entity.SettlementAgent;
import org.example.mapper.SettlementAgentMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
public class SettlementAgentModule {
    @Resource
    private SettlementAgentMapper settlementAgentMapper;

    /**
     * 根据结算主体id查询结算主体
     */
    public List<SettlementAgent> getAgentsById(List<String> ids){
        return settlementAgentMapper.selectBatchIds(ids);
    }

    /**
     * 根据结算主体id查询并转换为对应的map映射
     */
    public HashMap<String,SettlementAgent> getAgentMapById(List<String> ids){
        return getAgentsById(ids)
                .stream()
                .reduce(new HashMap<>(),(prev, curr)->{
                    prev.put(curr.getId(), curr);
                    return prev;
                },(l,r)->l);
    }

    /**
     * 给应收记录中的结算主体赋值
     */
    public void setAgent(SettlementAgent settlementAgent, ReceivableSettlement receivableSettlement) {

    }
}
