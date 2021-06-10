package com.fzshuai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-14 21:51
 */
@Controller
public class MusicShowController {

    @GetMapping("/music")
    public String about() {
        return "music";
    }
}
