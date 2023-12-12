package org.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
@TableName(value = "policy", autoResultMap = true)
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
    @TableField(fill = FieldFill.INSERT)
    private Long insureAt;
}
