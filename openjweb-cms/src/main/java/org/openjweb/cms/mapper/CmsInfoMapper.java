package org.openjweb.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.module.params.CmsInfoParam;

import java.util.List;
@Mapper
public interface CmsInfoMapper extends BaseMapper<CmsInfo> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CmsInfo> findPage(Page<?> page, @Param("param") CmsInfo param );

    @Select("SELECT * FROM cms_info WHERE row_id = #{rowId}")
        CmsInfo queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CmsInfo> queryList(@Param("param") CmsInfoParam param);


}
