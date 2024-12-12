package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.SmsSupplier;
import org.openjweb.core.module.params.SmsSupplierParam;

import java.util.List;
@Mapper
public interface SmsSupplierMapper extends BaseMapper<SmsSupplier> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<SmsSupplier> findPage(Page<?> page, @Param("param") SmsSupplier param );

    @Select("SELECT * FROM sms_supplier WHERE row_id = #{rowId}")
        SmsSupplier queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<SmsSupplier> queryList(@Param("param") SmsSupplierParam param);


}
