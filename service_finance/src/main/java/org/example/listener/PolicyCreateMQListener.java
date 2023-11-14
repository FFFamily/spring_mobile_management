package org.example.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.core.Policy;
import org.example.module.PaySettlementModule;
import org.example.module.ReceivableSettlementModule;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "policy_create")//监听的队列名称 TestDirectQueue
public class PolicyCreateMQListener {

    @RabbitHandler
    public void process(String message) {
        Policy policy = JSON.parseObject(message, Policy.class);
        log.info("保单承保消息  : {}", policy);
        try {
            ReceivableSettlementModule.addReceivableSettlementByPolicy(policy);
            PaySettlementModule.addPaySettlementByPolicy(policy);
        }catch (Exception e){
            log.error("保单生成应收应付记录出现错误，正在删除相关记录");
        }
    }
}
