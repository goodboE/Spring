package com.ko.shop.service;

import com.ko.shop.entity.User;
import com.ko.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(String userId, String password, String address) {

        if (userRepository.findByUserId(userId) == null) {
            User user = new User(userId, password, address);
            userRepository.save(user);
        }
        else
            throw new IllegalStateException("이미 존재하는 회원");
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


}
