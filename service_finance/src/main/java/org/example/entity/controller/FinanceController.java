package org.example.entity.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class FinanceController {
    @Autowired
    RabbitTemplate rabbitTemplate;
}
