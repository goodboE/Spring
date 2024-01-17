package com.ko.tricount.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpUserReq {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
}
