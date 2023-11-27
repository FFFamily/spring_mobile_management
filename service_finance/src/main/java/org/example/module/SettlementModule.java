package org.example.module;

import lombok.extern.slf4j.Slf4j;
import org.example.core.policy.PolicyDto;
import org.example.feign.AccountFeign;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 通用方法封装
 */
@Slf4j
@Component
public abstract class SettlementModule {
    @Resource
    private AccountFeign accountFeign;
    /**
     * 判断保费是否为0
     *
     * @param premium 保费
     */
    public static boolean checkPremium(Long premium) {
        if (premium != null && premium == 0) {
            log.info("保费为0不创建应收应付记录");
            return true;
        }
        // 此处保费为null也会放行，后续使用保费会产生空指针为正常情况
        return false;
    }

    public  boolean checkIsCertificated(String accountId){
       return accountFeign.findAccountByAccountId(accountId).getIsCertificated();
    }


    /**
     * 四舍五入
     */
    public static BigDecimal setScale(BigDecimal number) {
        if (number == null) return BigDecimal.ZERO;
        return number.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * rate 转 num ： 12.12% =》 0.1212
     */
    public static BigDecimal raleToNum(BigDecimal rate) {
        if (rate == null) return BigDecimal.ZERO;
        return rate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
    }


    /**
     * 是否已经过了续期时间，一般新单不会，但是线下单录入可能会
     *
     * @param policyDto      保单
     * @param periodIndex 期数
     * @return true 过了续期 false 没有过期
     */
    public static boolean checkReachRenewal(PolicyDto policyDto, int periodIndex) {
        return false;
    }}
