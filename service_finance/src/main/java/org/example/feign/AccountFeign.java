package org.example.feign;

import org.example.core.policy.PolicyDto;
import org.example.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("account")
public interface AccountFeign {
    @GetMapping("/account/findById")
    AccountDto findAccountByAccountId(String id);
}
