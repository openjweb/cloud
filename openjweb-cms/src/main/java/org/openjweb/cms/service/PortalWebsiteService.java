package org.openjweb.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.cms.entity.PortalWebsite;
import org.openjweb.cms.mapper.PortalWebsiteMapper;
import org.openjweb.cms.module.params.PortalWebsiteParam;
import org.openjweb.common.util.CMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PortalWebsiteService  extends ServiceImpl<PortalWebsiteMapper, PortalWebsite> {
    @Autowired
    private PortalWebsiteMapper portalWebsiteMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public PortalWebsite queryByRowId(String rowId){
        return this.portalWebsiteMapper.queryByRowId(rowId);
    }

    public List<PortalWebsite> queryList(PortalWebsiteParam param){
        List list = this.portalWebsiteMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<PortalWebsite> findPage(PortalWebsiteParam param){
        Page<PortalWebsite> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<PortalWebsite> list = this.portalWebsiteMapper.findPage(page,param);
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
            delCount = this.portalWebsiteMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
    public PortalWebsite getSiteInfo(HttpServletRequest request, PortalWebsiteParam param){
        PortalWebsite siteEnt = null;
        String domainName = CMSUtil.getDomainName(request);
        if(param==null)param = new PortalWebsiteParam();
        param.setDomainName(domainName);//设置域名
        List<PortalWebsite> list = this.queryList(param);
        if(list!=null&&list.size()>0){
            siteEnt = list.get(0);//获取站点信息
            //站点标题、站点ICP、站点主办单位名称、地址、版权信息、京公网安备、京ICP备等
        }

        return siteEnt;
    }
}
