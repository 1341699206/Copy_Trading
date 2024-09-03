package com.xtq_ymt.copy_trading_backend.controller;
 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
 
 
@RestController
public class TestController {
 
    @GetMapping("/test")
    public String test(){
        return "Hello Wrold !";
    }
    
}