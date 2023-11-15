package org.example.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.core.policy.Policy;
import org.example.entity.CommonException;

import org.example.mapper.PolicyMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PolicyService {
    @Resource
    private PolicyMapper policyMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建 Policy
     * @param policy
     */
    public void createPolicy(Policy policy){
        Policy oldPolicy = policyMapper.selectOne(new LambdaQueryWrapper<Policy>()
                .eq(Policy::getNo, policy.getNo()));
        if (oldPolicy != null){
            throw new CommonException("重复的policyNo");
        }
        policyMapper.insert(policy);
        rabbitTemplate.convertAndSend("RootDirectExchange","RootDirectRouting", JSON.toJSONString(policy));
    }
}
