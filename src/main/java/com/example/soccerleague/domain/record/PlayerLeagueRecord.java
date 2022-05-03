package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerLeagueRecord extends PlayerRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_league_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_id")
    private LeagueRound leagueRound;


    /***
     *  라인업 저장시 호출됨.
     * @param player
     * @param position
     * @param team
     * @param leagueround
     * @return
     */
    public static PlayerLeagueRecord create(Player player, Position position, Team team, LeagueRound leagueround){
        PlayerLeagueRecord playerLeagueRecord = new PlayerLeagueRecord();
        playerLeagueRecord.setPlayer(player);
        playerLeagueRecord.setLeagueRound(leagueround);
        playerLeagueRecord.setPosition(position);
        playerLeagueRecord.setSeason(leagueround.getSeason());
        playerLeagueRecord.setLocalDateTime(now());
        playerLeagueRecord.setTeam(team);
        return playerLeagueRecord;
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
            int goodDefense,int grade,MatchResult matchResult,boolean best,int rating
    ){
        this.setLocalDateTime(now());
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
        return "PlayerLeagueRecord{" +
                "leagueRound=" + leagueRound.getLeagueId() +
                ", player=" + player.getName() +
                ", team=" + team.getName() +
                ", season=" + position +
                '}';
    }
}
