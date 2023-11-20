package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaySettlement extends CommonEntity{
    // 保费
    private Long premium;
    // 应付佣金率
    private BigDecimal payRate;
    // 应付含税金额
    private Long includeTaxPayMoney;

}
