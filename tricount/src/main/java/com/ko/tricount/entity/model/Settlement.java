package com.ko.tricount.entity.model;


import com.ko.tricount.entity.SettlementParticipant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<SettlementParticipant> participants = new ArrayList<>();

    public Settlement(String name) {
        this.name = name;
    }
}
