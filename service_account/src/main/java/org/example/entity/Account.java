package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
// 业务员
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends CommonEntity {
    // 手机号
    private String mobile;
    // 业务员名称
    private String name;
    // 机构id
    private String orgId;
    // 是否认证
    private boolean isCertificated;
}
