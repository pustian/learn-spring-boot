package tian.pusen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tian.pusen.entity.Test;
import tian.pusen.mapper.TestMapper;
import tian.pusen.service.ITestService;

import java.util.List;

@Service
public class TestServiceImpl implements ITestService {
    @Autowired
    TestMapper testMapper;

    @Override
    public List<Test> getAll() {
        return testMapper.getAll();
    }
}
