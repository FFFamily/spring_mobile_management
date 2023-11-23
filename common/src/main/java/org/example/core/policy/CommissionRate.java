package org.example.core.policy;

import lombok.Data;

import java.util.HashMap;
@Data
public class CommissionRate {
    private HashMap<String, PromotionItemDto> promotion;
}
