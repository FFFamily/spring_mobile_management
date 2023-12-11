package org.example.controller;

import org.example.dto.SettlementAgentDto;
import org.example.entity.CommonResponse;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.service.SettlementAgentService;
import org.example.vo.SettlementAgentListRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/list")
    public CommonResponse<List<SettlementAgentDto>>  list(@RequestBody SettlementAgentListRequest request){
        return CommonResponse.success(settlementAgentService.list(request));
    }
}
