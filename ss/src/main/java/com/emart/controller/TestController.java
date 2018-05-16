package com.emart.controller;

import com.emart.service.TestService;
import com.emart.web.ShiroDBRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller("testController")
@Cacheable("testController")
@RequestMapping("/demo")
public class TestController {

    @Autowired
    private  TestService testService;
    @Autowired
    private   ShiroDBRealm shiroDbRealm;

    @ResponseBody
    @RequestMapping("/")
    public String Index() {
        int i = 1;
        return "sucess";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Map Home() {
        int i = 1;
        return testService.findone();
    }

    @RequestMapping("shiroPage")
    @RequiresPermissions(value = {"", ""})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("shiro/shiroPage");
        return modelAndView;
    }

    @RequestMapping("/clearCache")
    //    权限修改生效后 立即刷新系统缓存 则可以实现用户不退出生效的新的权限
    public Map clearCache() {
        //获取所有用户session信息
        PrincipalCollection principalCollections = SecurityUtils.getSubject().getPrincipals();
        shiroDbRealm.clearCache(principalCollections);
        return new HashMap();
    }


    @RequestMapping(value = "page-login")
    public String showPageLogin() {
        return "page-login";
    }

    @RequestMapping(value = "page-error")
    public String showPageError() {
        return "page-error";
    }

}
