package org.openjweb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommSystemParms;
import org.openjweb.core.mapper.CommSystemParmsMapper;
import org.openjweb.core.module.params.CommSystemParmsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CommSystemParmsService  extends ServiceImpl<CommSystemParmsMapper, CommSystemParms> {
    @Autowired
    private CommSystemParmsMapper commSystemParmsMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public CommSystemParms queryByRowId(String rowId){
        return this.commSystemParmsMapper.queryByRowId(rowId);
    }

    public List<CommSystemParms> queryList(CommSystemParmsParam param){
        List list = this.commSystemParmsMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<CommSystemParms> findPage(CommSystemParmsParam param){
        Page<CommSystemParms> page = new Page<>(param.getPageNo(), param.getPageSize());

        IPage<CommSystemParms> list = this.commSystemParmsMapper.findPage(page,param);
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
            delCount = this.commSystemParmsMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
