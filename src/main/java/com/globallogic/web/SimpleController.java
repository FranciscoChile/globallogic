package com.globallogic.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleController {

    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", "EY code challenge");
        return "home";
    }
}
