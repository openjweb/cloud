package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.module.params.CommApiKeyParam;

import java.util.List;
@Mapper
public interface CommApiKeyMapper extends BaseMapper<CommApiKey> {

    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommApiKey> findPage(Page<?> page, @Param("param") CommApiKey param );

    @Select("SELECT * FROM comm_api_key WHERE row_id = #{rowId}")
    CommApiKey queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommApiKey> queryList(@Param("param") CommApiKeyParam param);


}
