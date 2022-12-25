package com.foodlist.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestResource {

    @GetMapping("/user")
    public String user (){
        return ("user");
    }

    @GetMapping("/admin")
    public String admin (){
        return ("admin");
    }
}
