package com.ldf.sercurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lidefu
 * @date 2019/2/27 15:07
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${skip.urls}")
    private String skipUrls;

    /**
     * 注册 401 处理器
     * token 无效或者没有携带 token 时的处理
     */
    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    /**
     * 注册 403 处理器
     * 无权限时的处理
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(skipUrls.split(",")).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/user/loginout").addLogoutHandler(new MyLogoutHandler())
                .and()
                // 配置被拦截时的处理
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .accessDeniedHandler(this.accessDeniedHandler)
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //添加token认证过滤器
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        //禁用缓存
        http.headers().cacheControl();

    }

}
