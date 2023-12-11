package org.example.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.core.policy.PolicyDto;
import org.example.entity.CommonException;
import org.example.entity.Policy;
import org.example.entity.PromotionItem;
import org.example.mapper.PolicyMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PolicyService {
    @Resource
    private PolicyMapper policyMapper;
    @Resource
    private PromotionItemService promotionItemService;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建 Policy
     */
    @Transactional(rollbackFor = Exception.class)
    public void createPolicy(PolicyDto policyDto) {
        Policy oldPolicy = policyMapper.selectOne(new LambdaQueryWrapper<Policy>()
                .eq(Policy::getNo, policyDto.getNo()));
        if (oldPolicy != null) {
            throw new CommonException("重复的policyNo");
        }
        Policy policy = new Policy();

        BeanUtils.copyProperties(policyDto, policy);
        policyMapper.insert(policy);
        List<PromotionItem> list = policyDto.getPromotions().stream().map(item -> {
            PromotionItem promotionItem = new PromotionItem();
            promotionItem.setPolicyId(policy.getId());
            promotionItem.setName(item.getName());
            promotionItem.setPremium(item.getPremium());
            promotionItem.setFinalSettlementToOrg(item.getFinalSettlementToOrg());
            promotionItem.setOrgToAccount(item.getOrgToAccount());
            promotionItem.setInsuranceCompanyToSystem(item.getInsuranceCompanyToSystem());
            return promotionItem;
        }).toList();
        promotionItemService.insertList(list);
        policyDto.setId(policy.getId());
        rabbitTemplate.convertAndSend("RootDirectExchange", "RootDirectRouting", JSON.toJSONString(policyDto));
    }

    public PolicyDto findPolicyById(String policyId) {
        PolicyDto policyDto = policyMapper.findById(policyId);
        if (policyDto == null) {
            throw new CommonException("未能查询到对应的保单：" + policyId);
        }
        return policyDto;
    }
}
