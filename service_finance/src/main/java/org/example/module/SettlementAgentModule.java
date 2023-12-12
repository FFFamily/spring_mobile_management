package org.example.module;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.dto.SettlementAgentDto;
import org.example.entity.Bill;
import org.example.entity.CommonException;
import org.example.entity.receivable_settlement.ReceivableSettlement;
import org.example.entity.settlement_agent.SettlementAgentInfo;
import org.example.enums.SettlementAgentTypeEnum;
import org.example.enums.policy.PolicyTypeEnum;
import org.example.feign.PolicyFeign;
import org.example.mapper.BillMapper;
import org.example.mapper.SettlementAgentMapper;
import org.example.vo.SettlementAgentListRequest;
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
    @Resource
    private PolicyFeign policyFeign;
    @Resource
    private BillMapper billMapper;

    /**
     * 适用的范围：合并、结算主体拆分、共保拆分、创建应收信息
     */
    public static Optional<SettlementAgentInfo> getAgentCurrentSettlementInfo(SettlementAgentDto agent, Long createdAt) {
        if (agent == null || agent.getInfo() == null) {
            throw new CommonException("结算主体中的必要信息不能为空 agent: " + agent);
        }
        return agent.getInfo().stream().filter(info -> createdAt >= info.getFrom() && createdAt <= info.getEnd()).findFirst();
    }

    /**
     * 根据结算主体id查询结算主体
     */
    public List<SettlementAgentDto> getAgentsById(List<String> ids) {
        SettlementAgentListRequest request = new SettlementAgentListRequest();
        request.setAgentIdList(ids);
        return settlementAgentMapper.findList(request);
    }

    /**
     * 根据结算主体id查询并转换为对应的map映射
     */
    public HashMap<String, SettlementAgentDto> getAgentMapById(List<String> ids) {
        return getAgentsById(ids)
                .stream()
                .reduce(new HashMap<>(), (prev, curr) -> {
                    prev.put(curr.getId(), curr);
                    return prev;
                }, (l, r) -> l);
    }

    /**
     * 给应收记录中的结算主体赋值
     */
    public void setAgent(SettlementAgentDto agent, ReceivableSettlement receivableSettlement) {
        if (agent == null || receivableSettlement == null) {
            log.error("结算主体赋值错误，部分参数为null");
            return;
        }
        Optional<SettlementAgentInfo> optional;
        Long insuredAt = receivableSettlement.getInsuredAt();
        if (insuredAt != null) {
            optional = getAgentCurrentSettlementInfo(agent, receivableSettlement.getInsuredAt());
        } else {
            if (receivableSettlement.getPolicyType().equals(PolicyTypeEnum.POLICY.getCode())) {
                optional = getAgentInfoByPolicyId(agent, receivableSettlement.getPolicyId());
            } else {
                optional = getAgentInfoByEndorsementId(agent, receivableSettlement.getEndorsementId());
            }
        }
        if (optional.isPresent()) {
            SettlementAgentInfo infoItem = optional.get();
            receivableSettlement.setAgentIsIncludeTax(infoItem.getIsIncludeTax());
            receivableSettlement.setAgentTaxRate(infoItem.getTaxRate());
            receivableSettlement.setAgentFeeRate(infoItem.getFeeRate());
            // 应收开票项目
            if (infoItem.getBillId() != null) {
                Bill bill = billMapper.selectOne(new LambdaQueryWrapper<Bill>().eq(Bill::getId, infoItem.getBillId()));
                if (bill != null) {
                    receivableSettlement.setBillId(bill.getId());
                    receivableSettlement.setBillName(bill.getName());
                }
            }
        }
        if (SettlementAgentTypeEnum.DIRECT_SETTLE.getValue().equals(agent.getType())) {
            // 如果是保司结算
            receivableSettlement.setAgentTaxRate(receivableSettlement.getTaxRate());
            receivableSettlement.setAgentIsIncludeTax(receivableSettlement.getIsIncludeTax());
        }
        receivableSettlement.setAgentId(agent.getId());
        receivableSettlement.setAgentName(agent.getName());
        receivableSettlement.setAgentType(agent.getType());
    }

    private Optional<SettlementAgentInfo> getAgentInfoByEndorsementId(SettlementAgentDto settlementAgent, String endorsementId) {
        return Optional.empty();
    }

    /**
     * 判断保单是否匹配上对应的结算主体
     */
    private Optional<SettlementAgentInfo> getAgentInfoByPolicyId(SettlementAgentDto settlementAgent, String policyId) {
        PolicyDto policyDto = policyFeign.findPolicyByPolicyId(policyId).getData();
        log.info("远程查询 {} 保单信息: {}", policyId, policyDto);
        // 拿到承保时间
        Long insureAt = policyDto.getInsureAt();
        return getAgentCurrentSettlementInfo(settlementAgent, insureAt);
    }
}
