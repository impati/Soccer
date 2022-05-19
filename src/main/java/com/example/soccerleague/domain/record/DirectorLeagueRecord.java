package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.BaseEntity;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Team;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DirectorLeagueRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_league_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_id")
    private LeagueRound leagueRound;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MatchResult mathResult;

    private int rank;

}
