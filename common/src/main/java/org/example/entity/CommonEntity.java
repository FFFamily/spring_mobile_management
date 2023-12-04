package org.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonEntity implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Long createdAt;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedAt;

}
