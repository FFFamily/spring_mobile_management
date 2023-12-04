package org.example.controller;

import org.example.entity.CommonResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;


@RestController()
@RequestMapping("/finance")
public class FinanceController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    @ResponseBody
    public CommonResponse<Void> test(){
        System.out.println("接收到测试请求");
        HashMap<String, String> map = new HashMap<>();
        map.put("name","tutu");
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
        return CommonResponse.success();
    }

}
