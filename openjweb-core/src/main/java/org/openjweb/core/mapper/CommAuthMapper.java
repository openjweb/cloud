package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommAuth;
import org.openjweb.core.module.params.CommAuthParam;

import java.util.List;
@Mapper
public interface CommAuthMapper extends BaseMapper<CommAuth> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommAuth> findPage(Page<?> page, @Param("param") CommAuth param );

    @Select("SELECT * FROM comm_auth WHERE row_id = #{rowId}")
        CommAuth queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommAuth> queryList(@Param("param") CommAuthParam param);


}
