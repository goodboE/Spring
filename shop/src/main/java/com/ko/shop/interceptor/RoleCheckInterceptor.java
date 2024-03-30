package com.ko.shop.interceptor;

import com.ko.shop.entity.User;
import com.ko.shop.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (!currentUser.getUserId().equals("iwantjoin") || !currentUser.getPassword().equals("cjons")) {
            response.sendRedirect("/noPermission");
            log.info("권한 X");
            return false;
        }
        log.info("어드민");
        return true;
    }
}
