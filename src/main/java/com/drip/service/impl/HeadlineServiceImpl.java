package com.drip.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.pojo.Headline;
import com.drip.pojo.vo.PortalVo;
import com.drip.service.HeadlineService;
import com.drip.mapper.HeadlineMapper;
import com.drip.utils.JwtHelper;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author qq316
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-05-02 15:54:39
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    private HeadlineMapper headlineMapper;
    
    @Autowired
    private JwtHelper jwtHelper;
    /**
     * 1 进行分页查询
     * 2 拼接到result
     *  ！！！自定义查询
     *  ！！！返回List<Map>中
     * @param portalVo
     * @return
     */

    @Override
    public Result findNewsPage(PortalVo portalVo) {
        IPage page=new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);
        Map<String,Object> pageInfo =new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        return Result.ok(pageInfoMap);
    }

    /** 根据id查询详情
     * 2 查询对应数据 多表查询 （头条，用户表，类型表） 返回Map，因为没有实体类
     * 1 修改阅读量
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data=headlineMapper.queryDetailMap(hid);
        Map headlinemap=new HashMap();
        headlinemap.put("headline",data);
        Headline headline=new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) data.get("pageViews")+1); //阅读量+1
        headline.setVersion((Integer) data.get("version")); //设置版本
        headlineMapper.updateById(headline);
        return Result.ok(headlinemap);
    }

    @Override
    public Result publish(Headline headline,String token) {
        int userId = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("headline",headline);
        return Result.ok(pageInfoMap);
    }

    /**
     * 修改业务
     * 1.查询version版本
     * 2.补全属性,修改时间 , 版本!
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadLine(Headline headline) {

        //读取版本
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }


}




