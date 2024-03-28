package com.ko.shop.service;

import com.ko.shop.entity.User;
import com.ko.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(User user) {
        User existUser = userRepository.findById(user.getId()).orElse(null);

        if (existUser == null) {
            userRepository.save(user);
        }
        else
            throw new IllegalStateException("이미 존재하는 회원");
    }
}
