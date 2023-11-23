package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.PromotionItem;

import java.util.List;

@Mapper
public interface PromotionItemMapper extends BaseMapper<PromotionItem> {
    void savePromotionItems(List<PromotionItem> list);
}
