package com.ko.shop.controller;

import com.ko.shop.dto.ReviewForm;
import com.ko.shop.entity.Item;
import com.ko.shop.entity.Review;
import com.ko.shop.entity.User;
import com.ko.shop.service.ItemService;
import com.ko.shop.service.ReviewService;
import com.ko.shop.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/{orderItemId}")
    public String review(Model model) {
        model.addAttribute("reviewForm", new ReviewForm());
        return "review";
    }

    @PostMapping("/review/{orderItemId}")
    public String addReviewForm(@PathVariable(name = "orderItemId") Long orderItemId, ReviewForm reviewForm, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

        reviewService.save(currentUser.getId(), orderItemId, reviewForm.getContent());
        return "redirect:/myOrders";
    }

    @GetMapping("/reviews")
    public String items(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }


}
