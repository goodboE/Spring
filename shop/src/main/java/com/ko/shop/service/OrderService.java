package com.ko.shop.service;


import com.ko.shop.entity.*;
import com.ko.shop.enums.DeliveryStatus;
import com.ko.shop.exception.NotItemException;
import com.ko.shop.exception.NotUserException;
import com.ko.shop.repository.ItemRepository;
import com.ko.shop.repository.OrderRepository;
import com.ko.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long userId, Long itemId, int count) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new NotUserException();

        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null)
            throw new NotItemException();

        Delivery delivery = new Delivery(DeliveryStatus.READY, user.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(user, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null)
            order.cancel();
    }

}
