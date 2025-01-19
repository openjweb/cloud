package org.openjweb.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.PortalDynamicPic;
import org.openjweb.cms.mapper.PortalDynamicPicMapper;
import org.openjweb.cms.module.params.PortalDynamicPicParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PortalDynamicPicService  extends ServiceImpl<PortalDynamicPicMapper, PortalDynamicPic> {
    @Autowired
    private PortalDynamicPicMapper portalDynamicPicMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public PortalDynamicPic queryByRowId(String rowId){
        return this.portalDynamicPicMapper.queryByRowId(rowId);
    }

    public List<PortalDynamicPic> queryList(PortalDynamicPicParam param){
        List list = this.portalDynamicPicMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<PortalDynamicPic> findPage(PortalDynamicPicParam param){
        Page<PortalDynamicPic> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<PortalDynamicPic> list = this.portalDynamicPicMapper.findPage(page,param);
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
            delCount = this.portalDynamicPicMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
