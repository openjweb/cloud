package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommColumnDef;
import org.openjweb.core.module.params.CommColumnDefParam;

import java.util.List;
@Mapper
public interface CommColumnDefMapper extends BaseMapper<CommColumnDef> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommColumnDef> findPage(Page<?> page, @Param("param") CommColumnDef param );

    @Select("SELECT * FROM comm_column_def WHERE row_id = #{rowId}")
        CommColumnDef queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommColumnDef> queryList(@Param("param") CommColumnDefParam param);


}
