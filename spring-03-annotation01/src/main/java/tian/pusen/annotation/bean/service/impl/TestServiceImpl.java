package tian.pusen.annotation.bean.service.impl;

import tian.pusen.annotation.bean.service.ITestService;

public class TestServiceImpl implements ITestService {
    private String name;

    @Override
    public String sayHello() {
        return "hello " ;
    }

    public void setName(String name) {
        this.name = name;
    }
}
