package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.core.policy.PolicyDto;
import org.example.entity.Policy;


@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
    // 通过id查
    PolicyDto findById(String policyId);
    // 通过no查询
    PolicyDto findByNo(String no);
}
