package org.example.entity;

import lombok.Data;
import org.example.CommonEntity;
@Data
public class Bill extends CommonEntity {
    private String name;
    private Long createdAt;

}
