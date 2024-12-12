package ${basePackage}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openjweb.core.entity.CommApiKey;
import ${fullEntityClassName};
import ${basePackage}.module.params.${entityClassName}Param;

import java.util.List;
@Mapper
public interface ${entityClassName}Mapper extends BaseMapper<${entityClassName}> {
    /**
     * 带分页的查询
     * @param page
     * @param param
     * @return
     */

    IPage<${entityClassName}> findPage(Page<?> page, @Param("param") ${entityClassName} param );

    @Select("SELECT * FROM ${tableName} WHERE row_id = #{rowId}")
        ${entityClassName} queryByRowId(@Param("rowId") String rowId);

    /**
     * 不带分页的查询
     * @param param
     * @return
     */
    List<${entityClassName}> queryList(@Param("param") ${entityClassName}Param param);


}
