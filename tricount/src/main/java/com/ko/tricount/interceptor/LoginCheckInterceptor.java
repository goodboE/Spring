package com.ko.tricount.interceptor;

import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.UserService;
import com.ko.tricount.util.SessionConst;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            // 미인증 사용자 요청
            log.info("미인증 사용자 요청");
            return false;
        }

        // UserContext 에 User 주입
        try {
            User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
            UserContext.setCurrentUser(user);

//            if (em.contains(user)) {
//                log.info("user 는 영속상태입니다.");
//            } else {
//                log.info("user 는 영속상태가 아닙니다.");
//            }

            log.info("인증된 사용자 요청, user = {}", user.getLoginId());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "user info not found" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("로그아웃");
//        UserContext.clear();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
