package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Account;
import org.example.mapper.AccountMapper;
import org.example.mapper.OrgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Slf4j
@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 创建机构
     */
    public void addAccount(Account account) {
        log.info("【创建业务员】");
        accountMapper.insert(account);
    }

    public Account findAccountById(String accountId) {
        log.info("【查询业务员】{}",accountId);
        return  accountMapper.selectById(accountId);
    }
}
