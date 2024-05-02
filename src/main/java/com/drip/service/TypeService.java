package com.drip.service;

import com.drip.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drip.pojo.vo.PortalVo;
import com.drip.utils.Result;

/**
* @author qq316
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-05-02 15:54:39
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();


}
