package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.entity.CommonResponse;

import org.example.service.PolicyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/policy")
@Slf4j
public class PolicyController {
    @Resource
    PolicyService policyService;
    @PostMapping("/save")
    public CommonResponse<Void> createPolicy(@RequestBody PolicyDto request){
        log.info("开始创建Policy，{}",request);
        policyService.createPolicy(request);
        return CommonResponse.success(null);
    }

    @GetMapping("/getById/{policyId}")
    public CommonResponse<PolicyDto> findPolicyById(@PathVariable String policyId){
        log.info("查询指定id的保单信息，{}",policyId);
        return CommonResponse.success(policyService.findPolicyById(policyId));
    }
}
