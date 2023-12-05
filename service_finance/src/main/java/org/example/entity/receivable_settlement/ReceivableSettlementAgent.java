package org.example.entity.receivable_settlement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.settlement_agent.SettlementAgent;
import org.example.entity.settlement_agent.SettlementAgentInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivableSettlementAgent extends SettlementAgentInfo {
    // 结算主体id
    private String id;
    // 结算主体名称
    private String name;
    // 结算主体类型
    private Integer type;
    // 机构
    private String orgId;
}
