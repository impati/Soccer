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
public class TeamChampionsRecord extends TeamRecord{
    @ManyToOne
    @JoinColumn(name = "round_id")
    private ChampionsLeagueRound championsLeagueRound;
    /**
     *
     * line-up을 구축할때 호출
     * @param round
     * @param team
     * @return
     */

    public static TeamChampionsRecord create(Round round, Team team){
        TeamChampionsRecord teamChampionsRecord = new TeamChampionsRecord();
        teamChampionsRecord.setChampionsLeagueRound((ChampionsLeagueRound)round);
        teamChampionsRecord.setTeam(team);
        teamChampionsRecord.setSeason(round.getSeason());
        return teamChampionsRecord;
    }

}

