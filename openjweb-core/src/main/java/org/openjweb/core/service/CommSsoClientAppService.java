package org.openjweb.core.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommSsoClientApp;
import org.openjweb.core.mapper.CommSsoClientAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommSsoClientAppService   extends ServiceImpl<CommSsoClientAppMapper, CommSsoClientApp> {
    @Autowired
    private CommSsoClientAppMapper commSsoClientAppMapper;

    public CommSsoClientApp selectByAccountId(String accountId){


        CommSsoClientApp ent = this.commSsoClientAppMapper.selectByAccountId(accountId);



        return ent;

    }
}
