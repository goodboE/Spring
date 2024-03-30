package com.ko.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ElseController {

    @GetMapping("/noPermission")
    public String noPermission() {
        return "noPermission";
    }

    @GetMapping("/introduction")
    public String intro() {
        return "introduction";
    }
}
