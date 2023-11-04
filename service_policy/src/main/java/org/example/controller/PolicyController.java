package org.example.controller;

import org.example.entity.CommonResponse;
import org.example.service.PolicyService;
import org.example.vo.PolicyCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Resource
    PolicyService policyService;
    @PostMapping("/save")
    public CommonResponse createPolicy(PolicyCreateRequest request){
        policyService.createPolicy(request);
        return CommonResponse.success(null);
    }
}
