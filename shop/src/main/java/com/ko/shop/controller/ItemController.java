package com.ko.shop.controller;

import com.ko.shop.dto.ItemForm;
import com.ko.shop.dto.SignUpForm;
import com.ko.shop.entity.Item;
import com.ko.shop.service.ItemService;
import jakarta.validation.Valid;
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
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item")
    public String addItemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "/admin/item";
    }

    // 상품 등록
    @PostMapping("/admin/item")
    public String addItem(@Valid ItemForm itemForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "admin/item";

        itemService.addItem(itemForm.getName(), itemForm.getContent(), itemForm.getPrice(), itemForm.getQuantity());

        return "redirect:/items";
    }

    // 상품 조회
    @GetMapping("/items")
    public String getItems(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    // 상품 수정 폼
    @GetMapping("/admin/item/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item existItem = itemService.findById(itemId);
        model.addAttribute("itemForm", new ItemForm(existItem.getName(), existItem.getContent(), existItem.getPrice(), existItem.getQuantity()));

        return "admin/updateItemForm";
    }

    // 상품 수정
    @PostMapping("/admin/item/{itemId}/edit")
    public String updateItem(@PathVariable(name = "itemId") Long itemId, @ModelAttribute("itemForm") ItemForm itemForm) {
        itemService.updateItem(itemId, itemForm.getName(), itemForm.getContent(), itemForm.getPrice(), itemForm.getQuantity());
        return "redirect:/items";
    }
}
