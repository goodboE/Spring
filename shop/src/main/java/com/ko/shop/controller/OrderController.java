package com.ko.shop.controller;


import com.ko.shop.dto.OrderForm;
import com.ko.shop.entity.Order;
import com.ko.shop.entity.OrderItem;
import com.ko.shop.entity.User;
import com.ko.shop.service.ItemService;
import com.ko.shop.service.OrderItemService;
import com.ko.shop.service.OrderService;
import com.ko.shop.service.UserService;
import com.ko.shop.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    // 주문 폼
    @GetMapping("/order/{itemId}")
    public String createOrderForm(@PathVariable(name = "itemId") Long itemId, Model model) {
        OrderForm orderForm = new OrderForm();
        model.addAttribute("orderForm", orderForm);
        return "order";
    }

    // 단일 물품 주문
    @PostMapping("/order/{itemId}")
    public String order(@PathVariable(name = "itemId") Long itemId, HttpServletRequest request, @ModelAttribute("orderForm") OrderForm orderForm, BindingResult result) {
        if (result.hasErrors())
            return "order/{itemId}";
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

        orderService.order(user.getId(), itemId, orderForm.getCount(), orderForm.getAddress());
        return "redirect:/items";
    }

    // 내 주문 목록
    @GetMapping("/myOrders")
    public String getMyOrders(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Order> myOrders = orderService.findByUserId(user.getId());
        model.addAttribute("myOrders", myOrders);
        log.info("myOrders: {}", myOrders);
        return "myOrders";
    }

    // 전체 주문 목록 검색
    @GetMapping("/admin/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/admin/orders/{orderId}")
    public String cancelOrder(@PathVariable(name = "orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/admin/orders";
    }


    // 전체 주문
    @GetMapping("/orders")
    public String getOrderItems(Model model) {
        List<OrderItem> orderItems = orderItemService.findAll();
        model.addAttribute("orderItems", orderItems);
        return "/orders";
    }

}
