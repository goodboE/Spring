package com.ko.tricount.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ko.tricount.entity.SettlementParticipant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    @JsonIgnore
    private String password;
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<SettlementParticipant> participants = new ArrayList<>();

    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public User(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }
}
