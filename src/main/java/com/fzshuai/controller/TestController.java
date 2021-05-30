package com.fzshuai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-04-15 23:45
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello (){
        return "hello world";
    }
}
