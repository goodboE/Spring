package com.ko.tricount.entity.model;


import com.ko.tricount.entity.SettlementParticipant;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settlement {

    @Id @GeneratedValue
    @Column(name = "sett_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "settlement")
    private Set<SettlementParticipant> participants = new HashSet<>();


    public Settlement(String name) {
        this.name = name;
    }
}
