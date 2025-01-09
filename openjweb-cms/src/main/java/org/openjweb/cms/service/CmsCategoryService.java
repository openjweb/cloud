package org.openjweb.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsCategory;
import org.openjweb.cms.mapper.CmsCategoryMapper;
import org.openjweb.cms.module.params.CmsCategoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CmsCategoryService  extends ServiceImpl<CmsCategoryMapper, CmsCategory> {
    @Autowired
    private CmsCategoryMapper cmsCategoryMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public CmsCategory queryByRowId(String rowId){
        return this.cmsCategoryMapper.queryByRowId(rowId);
    }

    public List<CmsCategory> queryList(CmsCategoryParam param){
        List list = this.cmsCategoryMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<CmsCategory> findPage(CmsCategoryParam param){
        Page<CmsCategory> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<CmsCategory> list = this.cmsCategoryMapper.findPage(page,param);
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
            delCount = this.cmsCategoryMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
