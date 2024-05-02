package com.drip.controller;


import com.drip.pojo.Headline;
import com.drip.pojo.vo.PortalVo;
import com.drip.service.HeadlineService;
import com.drip.service.TypeService;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("portal")
@RestController
@CrossOrigin
public class PortalController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result=typeService.findAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result=headlineService.findNewsPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        return headlineService.showHeadlineDetail(hid);
    }

}
