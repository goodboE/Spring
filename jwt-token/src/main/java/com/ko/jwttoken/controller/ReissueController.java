package com.ko.jwttoken.controller;

import com.ko.jwttoken.entity.RefreshEntity;
import com.ko.jwttoken.jwt.JWTUtil;
import com.ko.jwttoken.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // get refresh token
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh"))
                refreshToken = cookie.getValue();

        }

        if (refreshToken == null)
            return new ResponseEntity<>("refresh token is null..", HttpStatus.BAD_REQUEST);

        // expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token is expired..", HttpStatus.BAD_REQUEST);
        }

        // is refresh token ?
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh"))
            return new ResponseEntity<>("invalid refresh token..", HttpStatus.BAD_REQUEST);

        // db 에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
        if (!isExist)
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);

        System.out.println("refresh token 존재");
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new AccessToken, RefreshToken
        String newAccessToken = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

        // db 안의 기존 토큰 삭제 후 새 토큰 저장
        refreshRepository.deleteByRefresh(refreshToken);
        addRefreshEntity(username, refreshToken, 86400000L);

        // response
        response.setHeader("access", newAccessToken);
        response.addCookie(createCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        // cookie.setSecure(true);
        // cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshEntity refreshEntity = new RefreshEntity(username, refresh, date.toString());
        refreshRepository.save(refreshEntity);
    }

}
