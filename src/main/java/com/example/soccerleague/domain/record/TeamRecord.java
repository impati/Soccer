package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public  class TeamRecord extends Record{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_record_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="team_id")
    protected Team team;


    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;



    /**
     *
     * line-up을 구축할때 호출
     * @param round
     * @param team
     * @return
     */

    public static TeamRecord create(Round round, Team team){
        TeamRecord teamLeagueRecord = null;
        if(round instanceof LeagueRound){
            teamLeagueRecord = new TeamLeagueRecord();
        }
        else if(round instanceof  ChampionsLeagueRound){
            teamLeagueRecord = new TeamChampionsRecord();
        }
        teamLeagueRecord.setRound(round);
        teamLeagueRecord.setTeam(team);
        teamLeagueRecord.setSeason(round.getSeason());
        return teamLeagueRecord;
    }
    /**
     * 경기종료시 호출
     */
    public void update(
            int score,int oppositeScore,int share,
            int cornerKick,int freeKick,int pass,
            int shooting,int validShooting,int foul,
            int goodDefense,double grade,MatchResult matchResult,double rating
    ){
        this.setScore(score);
        this.setOppositeScore(oppositeScore);
        this.setPass(pass);
        this.setShooting(shooting);
        this.setValidShooting(validShooting);
        this.setFoul(foul);
        this.setGoodDefense(goodDefense);
        this.setGrade(grade);
        this.setMatchResult(matchResult);
        this.setRating(rating);
        this.setCornerKick(cornerKick);
        this.setFreeKick(freeKick);
        this.setShare(share);
    }



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
    protected MatchResult matchResult;


    // 팀의 평점 . 선수들의 평균 평점.
    protected double grade;

    protected double rating;
    protected int rank;
}
