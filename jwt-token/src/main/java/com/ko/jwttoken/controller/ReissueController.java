package com.ko.jwttoken.controller;

import com.ko.jwttoken.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;

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

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new AccessToken
        String newAccessToken = jwtUtil.createJwt("access", username, role, 600000L);

        // response
        response.setHeader("access", newAccessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
