package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.BaseEntity;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectorRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_league_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_id")
    private Round round;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MatchResult mathResult;

    private int rank;


    public static DirectorRecord create(Round round , Director director){
        DirectorRecord directorRecord = new DirectorRecord();
        directorRecord.setRound(round);
        directorRecord.setTeam(director.getTeam());
        directorRecord.setDirector(director);
        return directorRecord;
    }
    public void update(MatchResult mathResult){
        this.mathResult = mathResult;
    }













}
