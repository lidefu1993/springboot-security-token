package com.ldf.sercurity.config;

import com.ldf.sercurity.helper.token.AnalysisResult;
import com.ldf.sercurity.helper.token.TokenHelper;
import com.ldf.sercurity.model.UserInfo;
import com.ldf.sercurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lidefu
 * @date 2019/2/27 15:11
 */
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Value("{token.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("----------------------AuthenticationTokenFilter-----------------------");
        System.out.println(userService.test());
        // 尝试获取请求头的 token
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String authToken = httpRequest.getHeader(tokenHeader);
        AnalysisResult analysisResult = TokenHelper.analysisToken(authToken, TokenHelper.TOKEN_SECRET, UserInfo.class);
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入
        if (analysisResult != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            analysisResult.setSuccess(true);
            // 检查用户带来的 token 是否有效
            if (analysisResult.isSuccess()) {
                // 生成通过认证
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(analysisResult.getResult(), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                // 将权限写入本次会话
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        chain.doFilter(request, response);
    }

}
