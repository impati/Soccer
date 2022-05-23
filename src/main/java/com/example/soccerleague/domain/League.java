package com.example.soccerleague.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class League extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="league_id")
    private Long id;
    private String name;
    private int rating;

    private int currentSeason;
    private int currentRoundSt;


    public League(String name){
        this.name = name;
    }

    public League(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
