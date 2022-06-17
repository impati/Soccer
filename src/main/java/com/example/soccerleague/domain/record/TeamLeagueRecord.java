package com.example.soccerleague.domain.record;

import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamLeagueRecord extends TeamRecord{

    @ManyToOne
    @JoinColumn(name = "round_id")
    private LeagueRound round;

    /**
     *
     * line-up을 구축할때 호출
     * @param round
     * @param team
     * @return
     */

    public static TeamLeagueRecord create(Round round, Team team){
        TeamLeagueRecord teamLeagueRecord = new TeamLeagueRecord();
        teamLeagueRecord.setRound((LeagueRound)round);
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


    @Override
    public String toString() {
        return "TeamLeagueRecord{" +
                "team=" + team.getName() +
                ", score=" + score +
                ", oppositeScore=" + oppositeScore +
                '}' + round.getId();
    }
}
