package org.openjweb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.SmsEntAccount;
import org.openjweb.core.mapper.SmsEntAccountMapper;
import org.openjweb.core.module.params.SmsEntAccountParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SmsEntAccountService  extends ServiceImpl<SmsEntAccountMapper, SmsEntAccount> {
    @Autowired
    private SmsEntAccountMapper smsEntAccountMapper;

    /**
     * 根据ROWID查询
     * @param rowId
     * @return
     */
    public SmsEntAccount queryByRowId(String rowId){
        return this.smsEntAccountMapper.queryByRowId(rowId);
    }

    public List<SmsEntAccount> queryList(SmsEntAccountParam param){
        List list = this.smsEntAccountMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     * @param param
     * @return
     */

    public  IPage<SmsEntAccount> findPage(SmsEntAccountParam param){
        Page<SmsEntAccount> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<SmsEntAccount> list = this.smsEntAccountMapper.findPage(page,param);
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
            delCount = this.smsEntAccountMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }
}
