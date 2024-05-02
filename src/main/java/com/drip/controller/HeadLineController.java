package com.drip.controller;

import com.drip.pojo.Headline;
import com.drip.service.HeadlineService;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadLineController {
    @Autowired
    private HeadlineService headlineService;

    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        return headlineService.publish(headline,token);

    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return result;
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadLine(headline);
        return result;
    }

    @PostMapping("removeByHid")
    public Result removeById(Integer hid){
        headlineService.removeById(hid);
        return Result.ok(null);
    }

}
