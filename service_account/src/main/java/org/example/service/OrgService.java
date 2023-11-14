package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Org;
import org.example.mapper.OrgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrgService {
    @Resource
    private OrgMapper orgMapper;

    /**
     * 创建机构
     */
    public void addOrg(Org org) {
        log.info("【创建机构】");
        orgMapper.insert(org);
    }
}
