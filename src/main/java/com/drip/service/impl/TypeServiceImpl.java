package com.drip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.pojo.Type;
import com.drip.pojo.vo.PortalVo;
import com.drip.service.TypeService;
import com.drip.mapper.TypeMapper;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qq316
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-05-02 15:54:39
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{
    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);

    }

}




