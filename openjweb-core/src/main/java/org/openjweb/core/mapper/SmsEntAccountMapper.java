package org.openjweb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.entity.SmsEntAccount;
import org.openjweb.core.module.params.SmsEntAccountParam;

import java.util.List;
@Mapper
public interface SmsEntAccountMapper extends BaseMapper<SmsEntAccount> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<SmsEntAccount> findPage(Page<?> page, @Param("param") SmsEntAccount param );

    @Select("SELECT * FROM sms_ent_account WHERE row_id = #{rowId}")
        SmsEntAccount queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<SmsEntAccount> queryList(@Param("param") SmsEntAccountParam param);


}
