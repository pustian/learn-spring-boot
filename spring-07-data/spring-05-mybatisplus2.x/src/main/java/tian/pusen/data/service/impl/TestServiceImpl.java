package tian.pusen.data.service.impl;

import tian.pusen.data.entity.Test;
import tian.pusen.data.mapper.TestMapper;
import tian.pusen.data.service.ITestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类
 * </p>
 *
 * @author pustian
 * @since 2020-04-06
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
