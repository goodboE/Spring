package com.ko.jwttoken.service;


import com.ko.jwttoken.dto.JoinDTO;
import com.ko.jwttoken.entity.UserEntity;
import com.ko.jwttoken.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) return;

        UserEntity user = new UserEntity(username, bCryptPasswordEncoder.encode(password), "ROLE_ADMIN");
        userRepository.save(user);
    }

}
