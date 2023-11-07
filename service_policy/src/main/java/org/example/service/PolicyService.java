package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.entity.CommonException;
import org.example.entity.Policy;
import org.example.mapper.PolicyMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
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
     * @param request
     */
    public void createPolicy(Policy policy){
        Policy oldPolicy = policyMapper.selectOne(new LambdaQueryWrapper<Policy>()
                .eq(Policy::getNo, policy.getNo()));
        if (oldPolicy != null){
            throw new CommonException("重复的policyNo");
        }
        policyMapper.insert(policy);
        rabbitTemplate.convertAndSend("RootDirectExchange","RootDirectRouting",policy);
    }
}
