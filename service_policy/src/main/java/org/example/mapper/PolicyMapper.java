package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.core.policy.Policy;


@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
    // 通过id查
    Policy findById(String policyId);
}
