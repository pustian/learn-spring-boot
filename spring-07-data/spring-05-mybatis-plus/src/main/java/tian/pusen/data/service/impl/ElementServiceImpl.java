package tian.pusen.data.service.impl;

import tian.pusen.data.entity.Element;
import tian.pusen.data.mapper.ElementMapper;
import tian.pusen.data.service.IElementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据项 服务实现类
 * </p>
 *
 * @author pustian
 * @since 2020-04-06
 */
@Service
public class ElementServiceImpl extends ServiceImpl<ElementMapper, Element> implements IElementService {

}
