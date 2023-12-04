package org.example.entity.settlement_agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementAgent extends CommonEntity {
    // 结算主体名称
    private String name;
    // 结算主体类型
    private Integer type;
    // 机构
    private String orgId;
    // 内容
    private List<SettlementAgentInfo> info;
}
