package com.ko.shop.service;

import com.ko.shop.entity.Item;
import com.ko.shop.entity.Order;
import com.ko.shop.entity.User;
import com.ko.shop.enums.OrderStatus;
import com.ko.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {

        User user = new User("testuser", "incheon");
        em.persist(user);

        Item item = new Item("item", "description..", 10000, 10 );
        em.persist(item);

        Long orderId = orderService.order(user.getId(), item.getId(), 2);

        Order order = orderRepository.findById(orderId).orElse(null);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(10000 * 2);
        assertThat(item.getQuantity()).isEqualTo(10-2);
    }
}