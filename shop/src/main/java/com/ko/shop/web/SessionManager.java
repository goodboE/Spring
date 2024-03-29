package com.ko.shop.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final String SESSION_ID = "mySessionId";
    private Map<String, Object> sessionMap = new ConcurrentHashMap<>();

    public void createSession(HttpServletResponse response, Object value) {

        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_ID, sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = getCookie(request, SESSION_ID);
        if (sessionCookie == null)
            return null;

        return sessionMap.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = getCookie(request, SESSION_ID);
        if (sessionCookie != null)
            sessionMap.remove(sessionCookie.getValue());
    }

    private Cookie getCookie(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null)
            return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}
