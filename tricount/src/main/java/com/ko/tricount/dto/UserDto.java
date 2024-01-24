package com.ko.tricount.dto;

import com.ko.tricount.entity.SettlementParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private List<Long> settlements;

}
