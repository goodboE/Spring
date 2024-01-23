package com.ko.tricount.controller;


import com.ko.tricount.dto.LoginUserReq;
import com.ko.tricount.dto.SignUpUserReq;
import com.ko.tricount.dto.UserDto;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.UserService;
import com.ko.tricount.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /** 전체 회원 조회 */
    @GetMapping("users")
    public Result findAll() {
        List<User> users = userService.findAll();
//        List<UserDto> collect = users.stream()
//                .map(u -> new UserDto(u.getId(), u.getLoginId(), u.getPassword(), u.getNickname()))
//                .collect(Collectors.toList());

        List<UserDto> userDtos = users.stream()
                .map(u -> {
                    List<Long> settlementIds = u.getSettlements().stream()
                            .map(Settlement::getId)
                            .collect(Collectors.toList());
                    return new UserDto(u.getId(), u.getLoginId(), u.getPassword(), u.getNickname(), settlementIds);
                })
                .collect(Collectors.toList());


        return new Result(userDtos.size(), userDtos);
    }

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
        log.info("[UserController.login] user = {}", user.getLoginId());

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

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

}
