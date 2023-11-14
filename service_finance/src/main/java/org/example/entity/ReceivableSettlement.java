package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceivableSettlement extends CommonEntity {
    // 状态
    private Long status;
    // 创建场景
    private Long createdScene;
    // 应收类型
    private Long type;
    // 保单类型
    private Long policyType;
    // 保单id
    private String policyId;
    // 保单号
    private String policyNo;
    // 批单id
    private String endorsementId;
    // 批单号
    private String endorsementNo;
    // 开票项目id
    private String billId;
    // 开票项目名称
    private String billName;
    // 结算主体
    private Object agent;
    // 不含税保费
    private Long excludeTaxPremium;
    // 含税手续费
    private Long fee;
    // 手续费率
    private BigDecimal feeRate;
    // 手续费
    private Long feeMoney;
    // 不含税手续费
    private Long excludeTaxFee;
    // 是否开启
    private Boolean enabled;
    // 开启时间
    private Long enabledAt;
    // 创建时间
    private Long createdAt;
    // 业务员id
    private String personnelId;
    // 组织id
    private String orgId;
    // 保费
    private Long premium;
    // 是否含税结算
    private Boolean isIncludeTax;
    // 险种id
    private String insuranceId;
    // 期数
    private Long periodIndex;
    // 税率
    private BigDecimal taxRate;
    // 税金
    private Long taxMoney;
    // 结算日期
    private Long settleAt;
    // 承保时间
    private Long insuredAt;
    // 分项保费Id
    private String interfaceFieldId;
    // 分项保费名称
    private String interfaceFieldName;
    // 最终结算金额
    private Long includeTaxSettleMoney;

}
