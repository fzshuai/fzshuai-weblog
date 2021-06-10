package com.fzshuai.controller;

import com.fzshuai.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 软件二班傅同学
 * @description TODO
 * @date 2021-02-14 22:34
 */
@Controller
public class PictureShowController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/picture")
    public String picture(Model model){
        model.addAttribute("pictures",pictureService.listPicture());
        return "picture";
    }
}