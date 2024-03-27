package com.ko.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainRoute() {
        return "Main Routing..";
    }
}
