package com.ko.shop.data;

import com.ko.shop.entity.Item;
import com.ko.shop.entity.Order;
import com.ko.shop.entity.User;
import com.ko.shop.enums.OrderStatus;
import com.ko.shop.repository.ItemRepository;
import com.ko.shop.repository.OrderRepository;
import com.ko.shop.repository.UserRepository;
import com.ko.shop.service.ItemService;
import com.ko.shop.service.OrderService;
import com.ko.shop.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

// @Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        User admin = new User("iwantjoin", "cjons", "서울특별시 용산구 한강대로 366");
        User testUser = new User("test", "123", "인천광역시 부평구 부개동");
        userRepository.save(admin);
        userRepository.save(testUser);

        Item apple = new Item("사과", "국내산 사과입니다", 1500, 100);
        Item banana = new Item("바나나", "하와이에서 온 바나나", 5000, 40);
        Item graphicCard = new Item("그래픽카드", "CJFORCE 4070", 600000, 10);
        Item iphone = new Item("아이폰", "15 PRO", 2000000, 30);
        Item me = new Item("고현진", "열심히 합니다", 0, 1);
        itemRepository.save(apple);
        itemRepository.save(banana);
        itemRepository.save(graphicCard);
        itemRepository.save(iphone);
        itemRepository.save(me);



    }
}
