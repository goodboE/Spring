package com.ko.shop.service;

import com.ko.shop.entity.User;
import com.ko.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public User login(String userId, String password) {
        User findUser = userRepository.findByUserId(userId);

        if (findUser == null)
            return null;

        if (findUser.getPassword().equals(password))
            return findUser;
        else
            return null;
    }
}
