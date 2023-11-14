package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.CommonEntity;


@EqualsAndHashCode(callSuper = false)
@Data
public class Policy extends CommonEntity {
    // 保单号
    private String no;
    // 业务员id
    private String accountId;
}
