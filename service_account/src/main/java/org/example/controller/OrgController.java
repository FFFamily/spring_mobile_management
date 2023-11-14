package org.example.controller;

import org.example.entity.CommonResponse;
import org.example.entity.Org;
import org.example.service.OrgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/org")
public class OrgController {

    @Resource
    private OrgService orgService;

    /**
     * 创建机构
     */
    @PostMapping("/save")
    public CommonResponse<Void> addOrg(@RequestBody Org org){
        orgService.addOrg(org);
        return CommonResponse.success();
    }
}
