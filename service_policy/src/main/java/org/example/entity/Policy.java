package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.core.policy.PromotionItemDto;

import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Data
@TableName(value = "policy",autoResultMap = true)
public class Policy extends CommonEntity {
    // 保单号
    private String no;
    // 业务员id
    private String accountId;
    // 出单类型
    private Integer saleType;
    // 险种id
    private String insuranceId;
    // 金额支付方式
    private String payPeriodUnit;
    // 承保时间
    private Long insureAt;
}