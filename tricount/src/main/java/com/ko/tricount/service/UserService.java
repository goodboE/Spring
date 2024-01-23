package com.ko.tricount.service;


import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;

    /** 전체 조회 */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /** 단건 조회 */
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    /** 회원 가입 */
    @Transactional
    public User signUp(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());

        if (findUser != null) {
            throw new DuplicateKeyException("이미 해당 id의 유저가 존재합니다.");
        }

        return userRepository.save(user);
    }

    /** 로그인 */
    public User login(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());
        if (findUser == null || !Objects.equals(findUser.getPassword(), user.getPassword())) {
            return null;
        }

        return findUser;
    }




}
