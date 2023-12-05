package org.example.controller;

import org.example.dto.SettlementAgentDto;
import org.example.entity.CommonResponse;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.service.SettlementAgentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/settlement_agent")
public class SettlementAgentController {
    @Resource
    private SettlementAgentService settlementAgentService;

    @PostMapping("/save")
    public CommonResponse<Void> save(@RequestBody SettlementAgentDto settlementAgent){
        settlementAgentService.save(settlementAgent);
        return CommonResponse.success();
    }
}
