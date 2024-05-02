package com.drip.interceptors;

import com.drip.utils.JwtHelper;
import com.drip.utils.Result;
import com.drip.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *  有，有效 放行
 *      无效 不放行
 *  没有 不放行
 */
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    从请求头获取token
        String token=request.getHeader("token");
//    检测是否有效
        boolean expiration = jwtHelper.isExpiration(token);
//    有效放行，无效返回504状态json
        if(!expiration){
            return true;
        }
        Result result= Result.build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper objectMapper=new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().write(json);
        return false;
    }
}
