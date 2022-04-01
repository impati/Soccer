package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.Round;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerChampionsLeagueRecord extends PlayerRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_champions_league_record_id")
    private Long id;

    @OneToOne
    @JoinColumn(name ="round_id")
    private ChampionsLeagueRound championsLeagueRound;
}
