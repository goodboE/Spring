package com.ko.board.service;

import com.ko.board.entity.RefreshToken;
import com.ko.board.jwt.JWTUtil;
import com.ko.board.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        // get refreshToken
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh"))
                refreshToken = cookie.getValue();
        }

        if (refreshToken == null)
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);

        // expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // refresh token check
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh"))
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);

        // db 에 저장되어 있는 refreshToken 인지 확인
        Boolean isExist = refreshTokenRepository.existsByToken(refreshToken);
        if (!isExist)
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);


        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new AccessToken
        String newAccessToken = jwtUtil.createJWT("access", username, role, 600000L);

        // refresh rotate, make new RefreshToken
        // 저장되어 있는 올바른 토큰이라면, 기존 토큰을 db 에서 삭제 후 새로운 토큰 생성
        String newRefreshToken = jwtUtil.createJWT("refresh", username, role, 86400000L);
        refreshTokenRepository.deleteByToken(refreshToken);
        addRefreshToken(username, refreshToken, 86400000L);
        log.info("새로운 리프레쉬 토큰 저장: {}", refreshToken);

        // response
        response.setHeader("access", newAccessToken);
        response.addCookie(createCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshToken(String username, String token, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = new RefreshToken(username, token, date.toString());

        refreshTokenRepository.save(refreshToken);
    }
}
