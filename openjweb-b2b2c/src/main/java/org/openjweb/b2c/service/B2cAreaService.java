package org.openjweb.b2c.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.openjweb.b2c.entity.B2cArea;
import org.openjweb.b2c.mapper.B2cAreaMapper;
import org.openjweb.b2c.module.params.B2cAreaParam;
import org.openjweb.b2c.module.result.B2cAreaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAsync
@Service
public class B2cAreaService extends ServiceImpl<B2cAreaMapper, B2cArea> {

    @Autowired
    private B2cAreaMapper b2cAreaMapper;

    public IPage<B2cArea> list(B2cAreaParam param) {
        //http://localhost:8001/api/b2c/area?comId=C0001
        Page<B2cArea> page = new Page<>(param.getPage(), param.getPageSize());

        return b2cAreaMapper.selectPageWithCondition(page,param);

    }

    public IPage<B2cAreaResult> list2(B2cAreaParam param) {

        //http://localhost:8001/api/b2c/area?comId=C0001
        Page<B2cAreaResult> page = new Page<>(param.getPage(), param.getPageSize());

        return b2cAreaMapper.selectPageWithCondition2(page,param);
    }

    public B2cArea getObject(String rowId){

        QueryWrapper<B2cArea> wrapper = new QueryWrapper<B2cArea>();
        wrapper.eq("row_id",rowId);

        B2cArea ent = this.b2cAreaMapper.selectOne(wrapper);

        //另外一种查询方式
        B2cArea ent1  = this.b2cAreaMapper.selectByRowId(rowId);
        if(ent1!=null){
            //输出区域名称
            System.out.println(ent1.getAreaName());
        }
        return ent;
    }

    public void del(String selectedIds) throws  Exception {
        String[] ids = null;
        int delCount = 0;
        if(selectedIds!=null&&selectedIds.trim().length()>0){
            ids = selectedIds.split(",");
            //System.out.println(ids.length);
            List<String> parmList = Arrays.asList(ids);
            delCount = this.b2cAreaMapper.deleteBatchIds(parmList);
        }
        if(delCount==0){
            throw new  Exception ("删除失败!");
        }
    }



}