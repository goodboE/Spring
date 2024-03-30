package com.ko.shop.controller;

import com.ko.shop.dto.LoginForm;
import com.ko.shop.entity.User;
import com.ko.shop.service.LoginService;
import com.ko.shop.web.SessionConst;
import com.ko.shop.web.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String signInForm(@ModelAttribute("loginForm") LoginForm loginForm) {

        return "user/signInForm";
    }

    @PostMapping("/login")
    public String signIn(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors())
            return "user/signInForm";

        User loginUser = loginService.login(loginForm.getUserId(), loginForm.getPassword());
        if (loginUser == null) {
            log.info("null");
            bindingResult.reject("loginFail..", "아이디 또는 패스워드가 맞지 않습니다.");
            return "/user/signInForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        return "redirect:/";
    }
}
