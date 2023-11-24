package org.example.feign;

import org.example.core.policy.PolicyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("policy")
public interface PolicyFeign {
    @GetMapping("/policy/findById")
    PolicyDto findPolicyByPolicyId(String id);
}
