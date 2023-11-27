package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.CommonEntity;
@Getter
@Setter
public class AccountDto extends CommonEntity {
    // 手机号
    private String mobile;
    // 业务员名称
    private String name;
    // 机构id
    private String orgId;
    // 是否认证
    private Boolean isCertificated;
}
