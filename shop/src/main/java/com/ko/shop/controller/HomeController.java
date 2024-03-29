package com.ko.shop.controller;


import com.ko.shop.entity.User;
import com.ko.shop.web.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final SessionManager sessionManager;

//    @GetMapping("/")
//    public String home(HttpServletRequest request, Model model) {
//        User currentUser = (User) sessionManager.getSession(request);
//
//    }
}
