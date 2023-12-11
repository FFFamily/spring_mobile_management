package org.example.controller;

import org.example.dto.SettlementAgentDto;
import org.example.dto.SettlementProductDto;
import org.example.entity.CommonResponse;
import org.example.service.SettlementAgentService;
import org.example.service.SettlementProductService;
import org.example.vo.SettlementAgentListRequest;
import org.example.vo.SettlementProductListRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController()
@RequestMapping("/settlement_product")
public class SettlementProductController {
    @Resource
    private SettlementProductService settlementProductService;

    @PostMapping("/save")
    public CommonResponse<Void> save(@RequestBody SettlementProductDto settlementProductDto){
        settlementProductService.save(settlementProductDto);
        return CommonResponse.success();
    }
    @PostMapping("/list")
    public CommonResponse<List<SettlementProductDto>> list(@RequestBody SettlementProductListRequest settlementProductListRequest){
        return CommonResponse.success(settlementProductService.list(settlementProductListRequest));
    }
}
