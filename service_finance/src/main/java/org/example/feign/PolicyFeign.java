package org.example.feign;

import org.example.core.policy.Policy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient
public interface PolicyFeign {
    @GetMapping("/policy/findById")
    Policy findPolicyByPolicyId(String id);
}
