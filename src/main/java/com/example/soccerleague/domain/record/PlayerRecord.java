package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
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
public  class PlayerRecord extends Record{

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


    @ManyToOne
    @JoinColumn(name = "round_id")
    protected Round round;


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


    /***
     *
     *  라인업 저장시 호출됨.
     * @param player
     * @param position
     * @param team
     * @param round
     * @return
     */
    public static PlayerRecord create(Player player, Position position, Team team, Round round){
        PlayerRecord PlayerRecord = null;
        if(round instanceof LeagueRound){
            PlayerRecord = new PlayerLeagueRecord();
        }
        else if(round instanceof  ChampionsLeagueRound){
            PlayerRecord = new PlayerChampionsLeagueRecord();
        }
        PlayerRecord.setPlayer(player);
        PlayerRecord.setRound(round);
        PlayerRecord.setPosition(position);
        PlayerRecord.setSeason(round.getSeason());
        PlayerRecord.setTeam(team);
        return PlayerRecord;
    }



    /**
     * 경기가 끝난 후 호출됨.
     * @param goal
     * @param assist
     * @param pass
     * @param shooting
     * @param validShooting
     * @param foul
     * @param goodDefense
     * @param grade
     */
    public void update(
            int goal,int assist ,int pass,
            int shooting,int validShooting,int foul,
            int goodDefense,int grade,MatchResult matchResult,boolean best,double rating
    ){
        this.setGoal(goal);
        this.setAssist(assist);
        this.setPass(pass);
        this.setShooting(shooting);
        this.setValidShooting(validShooting);
        this.setFoul(foul);
        this.setGoodDefense(goodDefense);
        this.setGrade(grade);
        this.setMathResult(matchResult);
        this.setBest(best);
        this.setRating(rating);
    }

    @Override
    public String toString() {
        return "PlayerRecord{" +
                "player=" + player.getName() +
                ", team=" + team.getName() +
                ", round=" + round.getId() +
                '}';
    }
}
