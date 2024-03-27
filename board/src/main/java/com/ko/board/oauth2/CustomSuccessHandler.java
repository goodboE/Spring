package com.ko.board.oauth2;

import com.ko.board.dto.CustomOAuth2User;
import com.ko.board.entity.RefreshToken;
import com.ko.board.jwt.JWTUtil;
import com.ko.board.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // create tokens
        String accessToken = jwtUtil.createJWT("access", username, role, 600000L);
        String refreshToken = jwtUtil.createJWT("refresh", username, role, 86400000L);

        // save refreshToken in DB
        saveRefreshToken(username, refreshToken, 86400000L);

        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("http://localhost:8080");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void saveRefreshToken(String username, String token, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken(username, token, date.toString());
        refreshTokenService.save(refreshToken);
    }
}
