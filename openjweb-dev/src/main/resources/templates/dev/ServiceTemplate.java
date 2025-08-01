package ${basePackage}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ${fullClassName};
import ${basePackage}.mapper.${entityClassName}Mapper;
import ${basePackage}.module.params.${entityClassName}Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ${entityClassName}Service  extends ServiceImpl<${entityClassName}Mapper, ${entityClassName}> {
    @Autowired
    private ${entityClassName}Mapper ${classNameLower}Mapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public ${entityClassName} queryByRowId(String rowId){
        return this.${classNameLower}Mapper.queryByRowId(rowId);
    }

    public List<${entityClassName}> queryList(${entityClassName}Param param){
        List list = this.${classNameLower}Mapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<${entityClassName}> findPage(${entityClassName}Param param){
        Page<${entityClassName}> page = new Page<>(param.getPageNo(), param.getPageSize());

        IPage<${entityClassName}> list = this.${classNameLower}Mapper.findPage(page,param);
        return list;
    }

    /**
     * 批量删除
     * @param selectedIds
     * @throws Exception
     */
    public void del(String selectedIds) throws  Exception {
        String[] ids = null;
        int delCount = 0;
        if(selectedIds!=null&&selectedIds.trim().length()>0){
            ids = selectedIds.split(",");
            //System.out.println(ids.length);
            List<String> parmList = Arrays.asList(ids);
            delCount = this.${classNameLower}Mapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
