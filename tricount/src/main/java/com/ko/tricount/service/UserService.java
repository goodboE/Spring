package com.ko.tricount.service;


import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /** 회원 가입 */
    @Transactional
    public User signUp(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());
        if (findUser != null) {
            throw new DuplicateKeyException("이미 해당 id의 유저가 존재합니다.");
        }

        return userRepository.save(user);
    }

    /**
     * todo 로그인, 로그아웃
     */
    public User login(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());
        if (findUser == null || !Objects.equals(findUser.getPassword(), user.getPassword())) {
            return null;
        }

        return findUser;
    }


}
