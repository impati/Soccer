package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class TeamRecord extends Record{

    @ManyToOne
    @JoinColumn(name ="team_id")
    protected Team team;

    protected int score ;
    protected int oppositeScore;

    //점유율
    protected int share;
    //슈팅
    protected int shooting;
    // 유효 슈팅
    protected int validShooting;
    //코너 킥
    protected int cornerKick;
    //프리킥
    protected int freeKick;
    //파울
    protected int foul;
    //선방
    protected int GoodDefense;
    protected int pass;

    @Enumerated(EnumType.STRING)
    protected MatchResult mathResult;


    // 팀의 평점 . 선수들의 평균 평점.
    protected double grade;

    protected int rating;
    protected int rank;
}
