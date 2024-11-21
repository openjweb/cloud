package org.openjweb.sns.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sns")
public class SnsApi {

    @GetMapping("test")
    public String sayHello(){
        return "111111";
    }
}
