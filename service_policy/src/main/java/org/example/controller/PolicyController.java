package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.Policy;
import org.example.entity.CommonResponse;

import org.example.service.PolicyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/policy")
@Slf4j
public class PolicyController {
    @Resource
    PolicyService policyService;
    @PostMapping("/save")
    public CommonResponse<Void> createPolicy(@RequestBody Policy request){
        log.info("开始创建Policy，{}",request);
        policyService.createPolicy(request);
        return CommonResponse.success(null);
    }
}
