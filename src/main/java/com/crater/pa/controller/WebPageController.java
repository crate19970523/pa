package com.crater.pa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebPageController {
    @RequestMapping("/addTransaction")
    public String index() {
        return "addTransaction.html";
    }
}
