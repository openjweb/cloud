package org.openjweb.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.cms.entity.CmsCategory;
import org.openjweb.cms.module.params.CmsCategoryParam;

import java.util.List;
@Mapper
public interface CmsCategoryMapper extends BaseMapper<CmsCategory> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CmsCategory> findPage(Page<?> page, @Param("param") CmsCategory param );

    @Select("SELECT * FROM cms_category WHERE row_id = #{rowId}")
        CmsCategory queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CmsCategory> queryList(@Param("param") CmsCategoryParam param);


}
