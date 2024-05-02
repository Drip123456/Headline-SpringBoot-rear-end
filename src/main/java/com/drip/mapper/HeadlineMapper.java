package com.drip.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drip.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drip.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author qq316
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-05-02 15:54:39
* @Entity com.drip.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    IPage<Map> selectMyPage(IPage page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




