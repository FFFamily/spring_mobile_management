package org.example.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;


@EqualsAndHashCode(callSuper = false)
@Data
public class Policy extends CommonEntity {
    // 保单号
    private String no;
    // 业务员id
    private String accountId;
}
