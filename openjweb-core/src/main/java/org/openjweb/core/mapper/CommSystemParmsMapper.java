package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommSystemParms;
import org.openjweb.core.module.params.CommSystemParmsParam;

import java.util.List;
@Mapper
public interface CommSystemParmsMapper extends BaseMapper<CommSystemParms> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommSystemParms> findPage(Page<?> page, @Param("param") CommSystemParms param );

    @Select("SELECT * FROM comm_system_parms WHERE row_id = #{rowId}")
        CommSystemParms queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommSystemParms> queryList(@Param("param") CommSystemParmsParam param);


}
