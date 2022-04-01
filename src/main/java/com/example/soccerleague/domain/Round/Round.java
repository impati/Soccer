package com.example.soccerleague.domain.Round;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="round_Id")
    private Long id;

    protected Long homeTeamId;
    protected Long awayTeamId;
    protected Long leagueId;
    protected int season;
    protected int roundSt;

    @Enumerated(EnumType.STRING)
    protected RoundStatus roundStatus;
}
