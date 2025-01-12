package org.openjweb.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.cms.entity.PortalWebsite;
import org.openjweb.cms.module.params.PortalWebsiteParam;

import java.util.List;
@Mapper
public interface PortalWebsiteMapper extends BaseMapper<PortalWebsite> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<PortalWebsite> findPage(Page<?> page, @Param("param") PortalWebsite param );

    @Select("SELECT * FROM portal_website WHERE row_id = #{rowId}")
        PortalWebsite queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<PortalWebsite> queryList(@Param("param") PortalWebsiteParam param);


}
