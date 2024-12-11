package org.openjweb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommApiKey;
import org.openjweb.core.mapper.CommApiKeyMapper;
import org.openjweb.core.module.params.CommApiKeyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CommApiKeyService  extends ServiceImpl<CommApiKeyMapper, CommApiKey> {
    @Autowired
    private CommApiKeyMapper commApiKeyMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public CommApiKey queryByRowId(String rowId){
        return this.commApiKeyMapper.queryByRowId(rowId);
    }

    public List<CommApiKey> queryList(CommApiKeyParam param){
        log.info("调用this.commApiKeyMapper.queryList");
        List list = this.commApiKeyMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<CommApiKey> findPage(CommApiKeyParam param){
        Page<CommApiKey> page = new Page<>(param.getPage(), param.getPageSize());
        log.info("调用this.commApiKeyMapper.queryList");
        IPage<CommApiKey> list = this.commApiKeyMapper.findPage(page,param);
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
            delCount = this.commApiKeyMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
