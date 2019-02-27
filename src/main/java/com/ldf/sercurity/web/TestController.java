package com.ldf.sercurity.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidefu
 * @date 2019/2/27 15:04
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("1")
    public String test1(){
        System.out.println("----------test1--------");
        return "test - 1";
    }

    @GetMapping("2")
    public String test2(){
        System.out.println("----------test2---------");
        return "test - 2";
    }

}
