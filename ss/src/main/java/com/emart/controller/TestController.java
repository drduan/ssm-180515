package com.emart.controller;

import com.emart.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller("testController")
@RequestMapping("/demo")
public class TestController {

    @Resource
    TestService testService;

    @ResponseBody
    @RequestMapping( "/" )
    public String Index() {
        int i = 1;
        return"sucess";
    }
    @ResponseBody
    @RequestMapping( "/index" )
    public Map Home() {
        int i = 1;
        return testService.findone();
    }
}
