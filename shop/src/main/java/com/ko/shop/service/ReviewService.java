package com.ko.shop.service;


import com.ko.shop.entity.Order;
import com.ko.shop.entity.OrderItem;
import com.ko.shop.entity.Review;
import com.ko.shop.entity.User;
import com.ko.shop.repository.OrderItemRepository;
import com.ko.shop.repository.OrderRepository;
import com.ko.shop.repository.ReviewRepository;
import com.ko.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long save(Long userId, Long orderItemId, String content) {

        User user = userRepository.findById(userId).orElse(null);
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);

        Review review = new Review(user, orderItem, content);

        reviewRepository.save(review);
        return review.getId();
    }

    // public List<Review> findByItemId

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

}
