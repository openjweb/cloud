package org.openjweb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommCompany;
import org.openjweb.core.mapper.CommCompanyMapper;
import org.openjweb.core.module.params.CommCompanyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CommCompanyService  extends ServiceImpl<CommCompanyMapper, CommCompany> {
    @Autowired
    private CommCompanyMapper commCompanyMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public CommCompany queryByRowId(String rowId){
        return this.commCompanyMapper.queryByRowId(rowId);
    }

    public List<CommCompany> queryList(CommCompanyParam param){
        List list = this.commCompanyMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<CommCompany> findPage(CommCompanyParam param){
        Page<CommCompany> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<CommCompany> list = this.commCompanyMapper.findPage(page,param);
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
            delCount = this.commCompanyMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
