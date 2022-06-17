package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerRecord extends Record{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="player_id")
    protected Player player;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    protected Team team;



    protected int goal;
    protected int assist;
    protected int pass;
    //슈팅
    protected int shooting;
    // 유효 슈팅
    protected int validShooting;
    //파울
    protected int foul;
    //선방
    protected int goodDefense;

    protected double rating;



    @Enumerated(EnumType.STRING)
    protected Position position;

    @Enumerated(EnumType.STRING)
    protected MatchResult mathResult;

    protected boolean isBest;
    protected int grade;
    protected int rank;




}
