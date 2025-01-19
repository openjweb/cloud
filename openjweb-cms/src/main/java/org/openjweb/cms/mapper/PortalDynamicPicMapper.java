package org.openjweb.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.cms.entity.PortalDynamicPic;
import org.openjweb.cms.module.params.PortalDynamicPicParam;

import java.util.List;
@Mapper
public interface PortalDynamicPicMapper extends BaseMapper<PortalDynamicPic> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<PortalDynamicPic> findPage(Page<?> page, @Param("param") PortalDynamicPic param );

    @Select("SELECT * FROM portal_dynamic_pic WHERE row_id = #{rowId}")
        PortalDynamicPic queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<PortalDynamicPic> queryList(@Param("param") PortalDynamicPicParam param);


}
