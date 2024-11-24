package org.openjweb.b2c.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.openjweb.b2c.entity.B2cStore;
import org.openjweb.b2c.module.params.B2cStoreParam;

import java.util.List;

@Mapper
public interface B2cStoreMapper {
    public List<B2cStore> selectPageWithCondition(@Param("param") B2cStoreParam param);
}
