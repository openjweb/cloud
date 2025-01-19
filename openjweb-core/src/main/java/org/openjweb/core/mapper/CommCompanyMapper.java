package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.module.params.CommCompanyParam;

import java.util.List;
@Mapper
public interface CommCompanyMapper extends BaseMapper<CommCompany> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<CommCompany> findPage(Page<?> page, @Param("param") CommCompany param );

    @Select("SELECT * FROM comm_company WHERE row_id = #{rowId}")
        CommCompany queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<CommCompany> queryList(@Param("param") CommCompanyParam param);


}
