package org.example.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;
import org.example.entity.settlement_agent.SettlementAgentInfo;

import java.util.List;
@EqualsAndHashCode(callSuper = false)
@Data
public class SettlementAgentDto extends CommonEntity {
    // 结算主体名称
    private String name;
    // 结算主体类型
    private Integer type;
    // 机构
    private String orgId;
    // 内容
    private List<SettlementAgentInfo> info;
}
