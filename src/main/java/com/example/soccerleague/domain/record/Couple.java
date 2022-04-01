package com.example.soccerleague.domain.record;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long id;


    // pair(goalPlayerId, assistPlayerId)  - >제약조건
    private Long goalPlayerId;
    private Long assistPlayerId;
    private int value;
}
