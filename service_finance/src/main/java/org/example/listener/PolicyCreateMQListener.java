package org.example.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.module.PaySettlementModule;
import org.example.module.ReceivableSettlementModule;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RabbitListener(queues = "policy_create")//监听的队列名称 TestDirectQueue
public class PolicyCreateMQListener {

    @Resource
    private ReceivableSettlementModule receivableSettlementModule;
    @Resource
    private PaySettlementModule paySettlementModule;
    @RabbitHandler
    public void process(String message) {
        PolicyDto policyDto = JSON.parseObject(message, PolicyDto.class);
        log.info("保单承保消息  : {}", policyDto);
        try {
            receivableSettlementModule.addReceivableSettlementByPolicy(policyDto);
            paySettlementModule.addPaySettlementByPolicy(policyDto);
        }catch (Exception e){
            log.error("保单生成应收应付记录出现错误，正在删除相关记录: ",e);
        }
    }
}
