package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommTableDef;
import org.openjweb.core.module.params.CommTableDefParam;

import java.util.List;
@Mapper
public interface CommTableDefMapper extends BaseMapper<CommTableDef> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommTableDef> findPage(Page<?> page, @Param("param") CommTableDef param );

    @Select("SELECT * FROM comm_table_def WHERE row_id = #{rowId}")
        CommTableDef queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommTableDef> queryList(@Param("param") CommTableDefParam param);


}
