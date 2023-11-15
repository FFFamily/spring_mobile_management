package org.example.core.policy;

import lombok.Data;

import java.util.List;

@Data
public class QuotePlan {
    // 分项保费
    private List<CommissionRate> commissionRates;
}
