package com.ko.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    private String role;

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
