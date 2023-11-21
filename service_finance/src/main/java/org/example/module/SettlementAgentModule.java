package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.CommonException;
import org.example.entity.ReceivableSettlement;
import org.example.entity.SettlementAgent;
import org.example.enums.policy.PolicyTypeEnum;
import org.example.mapper.SettlementAgentMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        if (settlementAgent == null || receivableSettlement == null){
            log.error("结算主体赋值错误，部分参数为null");
            return;
        }
        Optional<SettlementAgent.SettlementAgentInfo> optional;
        Long insuredAt = receivableSettlement.getInsuredAt();
        if (insuredAt != null) {
            optional = getAgentCurrentSettlementInfo(settlementAgent, receivableSettlement.getInsuredAt());
        }
        if (receivableSettlement.getPolicyType().equals(PolicyTypeEnum.POLICY.getCode())) {
            optional =  getAgentInfoByPolicyId(settlementAgent, receivableSettlement.getPolicyId());
        } else {
            optional =  getAgentInfoByEndorsementId(settlementAgent, receivableSettlement.getEndorsementId());
        }
    }

    private Optional<SettlementAgent.SettlementAgentInfo> getAgentInfoByEndorsementId(SettlementAgent settlementAgent, String endorsementId) {
        return Optional.empty();
    }

    private Optional<SettlementAgent.SettlementAgentInfo> getAgentInfoByPolicyId(SettlementAgent settlementAgent, String policyId) {
        return Optional.empty();
    }


    /**
     * 适用的范围：合并、结算主体拆分、共保拆分、创建应收信息
     */
    public static Optional<SettlementAgent.SettlementAgentInfo> getAgentCurrentSettlementInfo(SettlementAgent agent, Long createdAt) {
        if (agent == null || agent.getInfo() == null) {
            throw new CommonException("结算主体中的必要信息不能为空 agent: "+agent);
        }
        return agent.getInfo().stream().filter(info -> createdAt >= info.getFrom() && createdAt <= info.getEnd()).findFirst();
    }
}
