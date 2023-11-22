package org.example.core.policy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

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
    // 分项保费
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<PromotionItem> Promotions;
    // 险种id
    private String insuranceId;
    // 金额支付方式
    private String payPeriodUnit;
    // 承保时间
    private Long insureAt;
}
