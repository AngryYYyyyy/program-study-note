package com.lxy.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 8:42
 * @Description：
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    public ModelAndView home() {
        System.out.println("HomeController");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("msg", "Welcome to Spring MVC");
        return modelAndView;
    }
}
