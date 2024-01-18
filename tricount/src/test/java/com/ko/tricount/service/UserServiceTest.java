package com.ko.tricount.service;

import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Test
    void 회원가입() {

        // given
        User user = User.builder()
                .loginId("id1")
                .password("pw1")
                .nickname("user1")
                .build();
        // when
        userService.signUp(user);

        // then
        User findUser = userRepository.findByLoginId("id1");
        assertEquals(user, findUser);
    }
}