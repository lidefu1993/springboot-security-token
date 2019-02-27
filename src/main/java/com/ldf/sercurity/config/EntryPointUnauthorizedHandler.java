package com.ldf.sercurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lidefu
 * @date 2019/2/27 15:53
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Value("${login.url}")
    private String loginUrl;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(loginUrl.isEmpty()){
            //返回json形式的错误信息
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().println("{\"code\":401,\"message\":\"token 无效！\",\"data\":\"\"}");
            httpServletResponse.getWriter().flush();
        }else {
            httpServletResponse.sendRedirect(loginUrl);
        }

    }
}
