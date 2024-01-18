package com.ko.tricount;


import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        userRepository.save(new User("id1", "pw1", "name-1"));
        userRepository.save(new User("id2", "pw2", "name-3"));
        userRepository.save(new User("id3", "pw3", "name-3"));
    }

}
