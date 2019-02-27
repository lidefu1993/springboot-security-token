package com.ldf.sercurity.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidefu
 * @date 2019/2/27 15:03
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("login")
    public String login(){
        System.out.println(" -------------- UserController login --------------");
        return "success";
    }

    @GetMapping("loginout")
    public String loginout(){
        System.out.println("---------------- loginout -----------------------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "success";
    }
}
