package org.example.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SettlementAgentListRequest implements Serializable {
    // 结算主体名称
    private String name;
    // 结算主体Id集合
    private List<String> agentIdList;
}
