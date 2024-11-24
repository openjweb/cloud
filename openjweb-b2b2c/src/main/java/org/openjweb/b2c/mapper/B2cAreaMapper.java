package org.openjweb.b2c.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.b2c.entity.B2cArea;
import org.openjweb.b2c.module.params.B2cAreaParam;
import org.openjweb.b2c.module.result.B2cAreaResult;

@Mapper
public interface B2cAreaMapper extends BaseMapper<B2cArea> {

    IPage<B2cArea> selectPageWithCondition(Page<?> page, @Param("param") B2cAreaParam condition);
    IPage<B2cAreaResult> selectPageWithCondition2(Page<?> page, @Param("param") B2cAreaParam condition);

    @Select("SELECT * FROM b2c_area WHERE row_id = #{rowId}")
    B2cArea selectByRowId(@Param("rowId") String rowId);
}
