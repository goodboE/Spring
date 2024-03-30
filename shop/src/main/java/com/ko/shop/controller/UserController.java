package com.ko.shop.controller;

import com.ko.shop.dto.SignUpForm;
import com.ko.shop.entity.User;
import com.ko.shop.service.UserService;
import com.ko.shop.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/sign")
    public String signInOrUp(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("not session");
            return "user/signInOrUp";
        }
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginUser == null) {
            log.info("not login user");
            return "user/signInOrUp";
        }
        model.addAttribute("user", loginUser);
        return "user/signedSignUp";
    }

    // 회원 가입 폼
    @GetMapping("/user/add")
    public String signUpForm(@ModelAttribute("signUpForm") SignUpForm signUpForm) {
        return "user/signUpForm";
    }

    // 회원 가입
    @PostMapping("/user/add")
    public String signIn(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm, BindingResult result) {

        if (result.hasErrors())
            return "user/signUpForm";

        userService.join(signUpForm.getUserId(), signUpForm.getPassword(), signUpForm.getAddress());

        return "redirect:/";

    }

    // 회원 조회
    @GetMapping("/admin/users")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }


}
