package org.example.core.policy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.entity.CommonEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class EndorsementDto extends CommonEntity {
    private String no;
}
