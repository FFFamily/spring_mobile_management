package org.example.feign;

import org.example.core.policy.PolicyDto;
import org.example.entity.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("policy")
public interface PolicyFeign {
    @GetMapping("/policy/getById/{id}")
    CommonResponse<PolicyDto> findPolicyByPolicyId(@PathVariable("id") String id);
}
