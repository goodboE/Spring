package com.ko.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpForm {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String address;
}
