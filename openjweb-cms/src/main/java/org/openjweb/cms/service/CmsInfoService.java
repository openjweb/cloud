package org.openjweb.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.cms.mapper.CmsInfoMapper;
import org.openjweb.cms.module.params.CmsInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CmsInfoService  extends ServiceImpl<CmsInfoMapper, CmsInfo> {
    @Autowired
    private CmsInfoMapper cmsInfoMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public CmsInfo queryByRowId(String rowId){
        return this.cmsInfoMapper.queryByRowId(rowId);
    }

    public List<CmsInfo> queryList(CmsInfoParam param){
        List list = this.cmsInfoMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<CmsInfo> findPage(CmsInfoParam param){
        Page<CmsInfo> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<CmsInfo> list = this.cmsInfoMapper.findPage(page,param);
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
            delCount = this.cmsInfoMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
