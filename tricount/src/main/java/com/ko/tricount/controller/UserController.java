package com.ko.tricount.controller;


import com.ko.tricount.dto.SignUpUserReq;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.UserService;
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

}
