package org.example.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.entity.CommonException;
import org.example.enums.FinanceRecordOriginTypeEnum;
import org.example.enums.SaleTypeEnum;
import org.example.module.process.Promotion;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RabbitListener(queues = "policy_create")//监听的队列名称 TestDirectQueue
public class PolicyCreateMQListener {
    @Resource
    private Promotion promotion;

    /**
     * 线下
     */
    private static void offline() {

    }

    @RabbitHandler
    public void process(String message) {
        try {
            PolicyDto policyDto = JSON.parseObject(message, PolicyDto.class);
            log.info("保单承保消息  : {}", policyDto);
            log.info("【开始添加保单应收信息】");
            int saleType = policyDto.getSaleType();
            SaleTypeEnum saleTypeEnum = SaleTypeEnum.of(saleType);
            switch (saleTypeEnum) {
                case ONLINE -> online(policyDto);
                case OFFLINE -> offline();
                default -> throw new CommonException("【应收应付】不支持的出单类型");
            }
            log.info("========生成应收应付记录完成==========");
        } catch (Exception e) {
            log.error("【生成应收应付信息失败】：", e);
        }
    }

    /**
     * 线上
     */
    private void online(PolicyDto policyDto) {
        String key = "income_record_add_" + policyDto.getAccountId();
//        synchronized (key) {
        // 邀请奖励 和 活动奖励 线上出单不会有应收
        log.info("【Online】添加保单对应的系统出单类型数据");
        promotion.AddReceivableByPromotion(policyDto, FinanceRecordOriginTypeEnum.SYSTEM);
//            promotion.AddPaySettlementByPromotion(policyDto, FinanceRecordOriginTypeEnum.SYSTEM, null);
//        }
    }
}
