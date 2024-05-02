package com.drip.service;

import com.drip.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drip.pojo.vo.PortalVo;
import com.drip.utils.Result;

/**
* @author qq316
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-05-02 15:54:39
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline,String token);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadLine(Headline headline);
}
