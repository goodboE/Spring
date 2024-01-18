package com.ko.tricount.controller;


import com.ko.tricount.dto.LoginUserReq;
import com.ko.tricount.dto.SignUpUserReq;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.UserService;
import com.ko.tricount.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 회원 가입 */
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@Valid @RequestBody SignUpUserReq signUpUserReq) {
        User user = User.builder()
                .loginId(signUpUserReq.getLoginId())
                .password(signUpUserReq.getPassword())
                .nickname(signUpUserReq.getNickname())
                .build();
        return new ResponseEntity<>(userService.signUp(user), HttpStatus.OK);
    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginUserReq loginUserReq,
                                      HttpServletRequest request) {

        User user = userService.login(new User(loginUserReq.getLoginId(), loginUserReq.getPassword()));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, user);

        return ResponseEntity.ok().build();
    }

    /** 로그 아웃 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
