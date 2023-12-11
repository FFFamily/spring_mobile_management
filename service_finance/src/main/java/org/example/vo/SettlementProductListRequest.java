package org.example.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SettlementProductListRequest implements Serializable {
    // 结算主体名称
    private Integer page;
    private Integer pageSize;
}
