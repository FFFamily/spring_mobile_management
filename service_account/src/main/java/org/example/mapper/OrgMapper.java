package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Org;

@Mapper
public interface OrgMapper extends BaseMapper<Org> {
}
