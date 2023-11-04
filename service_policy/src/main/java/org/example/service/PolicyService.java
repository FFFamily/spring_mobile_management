package org.example.service;

import org.example.entity.Policy;
import org.example.mapper.PolicyMapper;
import org.example.vo.PolicyCreateRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PolicyService {
    @Resource
    private PolicyMapper policyMapper;

    /**
     * 创建 Policy
     * @param request
     */
    public void createPolicy(PolicyCreateRequest request){
        Policy policy = new Policy();
        BeanUtils.copyProperties(request,policy);
        policyMapper.insert(policy);
    }
}
