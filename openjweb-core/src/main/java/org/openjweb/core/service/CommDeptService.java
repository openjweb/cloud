package org.openjweb.core.service;

import org.springframework.stereotype.Service;

@Service
public class CommDeptService {

    public String getDeptDemoName(String comId){
        return "研发部";
    }

}
