package com.emart.service;


import com.emart.dao.TestMapper;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("testService")
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper mapp;

    public Map findone() {
        return mapp.select1();

    }


//    权限修改生效后 立即刷新系统缓存 则可以实现用户不退出生效的新的权限
//    public void clearCache(){
//        PrincipalCollection principalCollections = SecurityUtils.getSubject().getPrincipals();
//        super.clear(principalCollections);
//    }
}
