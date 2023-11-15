package org.example.core.policy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Data
public class Policy extends CommonEntity {
    // 保单号
    private String no;
    // 业务员id
    private String accountId;
    // 出单类型
    private Integer saleType;
    // 分项保费
    private QuotePlan quotePlan;
}
