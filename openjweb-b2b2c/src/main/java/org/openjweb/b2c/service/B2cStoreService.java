package org.openjweb.b2c.service;

import org.openjweb.b2c.entity.B2cStore;
import org.openjweb.b2c.mapper.B2cStoreMapper;
import org.openjweb.b2c.module.params.B2cStoreParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class B2cStoreService {
    @Autowired
    private B2cStoreMapper b2cStoreMapper;

    public List<B2cStore> getB2cStore(B2cStoreParam param){
        return b2cStoreMapper.selectPageWithCondition(param);
    }
}
