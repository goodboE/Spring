package com.ko.tricount.entity.model;


import com.ko.tricount.entity.SettlementParticipant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settlement {

    @Id @GeneratedValue
    @Column(name = "sett_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SettlementParticipant> participants = new HashSet<>();

    @Transient
    public Set<User> getUsers() {
        return participants.stream()
                .map(SettlementParticipant::getUser)
                .collect(Collectors.toSet());
    }

    public Settlement(String name) {
        this.name = name;
    }
}
