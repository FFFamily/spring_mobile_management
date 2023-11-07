package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
@Slf4j
@Component
@RabbitListener(queues = "policy_create")//监听的队列名称 TestDirectQueue
public class PolicyCreateMQListener {

    @RabbitHandler
    public void process(Map testMessage) {
        log.info("DirectReceiver消费者收到消息  : {}", testMessage.toString());
    }
}
