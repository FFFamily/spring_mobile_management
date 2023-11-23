package org.example.service;

import org.example.core.policy.PromotionItemDto;
import org.example.entity.PromotionItem;
import org.example.mapper.PromotionItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PromotionItemService {
    @Resource
    private PromotionItemMapper promotionItemMapper;

    /**
     * 批量添加
     */
    public void insertList(List<PromotionItem> list) {
        promotionItemMapper.savePromotionItems(list);
    }
}
