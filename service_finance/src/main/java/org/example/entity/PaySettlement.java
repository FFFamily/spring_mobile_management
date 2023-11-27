package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaySettlement extends CommonEntity{
    // 保费
    private Long premium;
    // 保单|批单类型
    private Integer policyType;
    // 保单id
    private String policyId;
    // 保单no
    private String policyNo;
    // 批单id
    private String endorsementId;
    // 批单no
    private String endorsementNo;
    // 创建场景
    private Integer createdScene;
    // 期数
    private Integer periodIndex;
    // 应付佣金率
    private BigDecimal payRate;
    // 应付含税金额
    private Long includeTaxPayMoney;
    // 对业务员
    private String accountId;
    // 业务员佣金
    private Long accountPromotion;
    // 对机构
    private String orgId;
    private Long orgPromotion;
    // 最终结算
    private String finalId;
    private Long finalPromotion;

}
