package com.emart.service;

import com.emart.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("testService")
public class TestService {

    @Autowired
    TestMapper mapp;

    @Cacheable(cacheNames = "cacheTest")
    public Map findone() {
        return mapp.select1();
    }
}
