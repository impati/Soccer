package com.example.soccerleague.domain.record;

import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfo;
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

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerChampionsLeagueRecord extends PlayerRecord{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="round_id")
    private ChampionsLeagueRound championsLeagueRound;
    /***
     *
     *  라인업 저장시 호출됨.
     * @param player
     * @param position
     * @param team
     * @param championsLeagueRound
     * @return
     */
    public static PlayerChampionsLeagueRecord create(Player player, Position position, Team team, ChampionsLeagueRound championsLeagueRound){
        PlayerChampionsLeagueRecord playerChampionsLeagueRecord = new PlayerChampionsLeagueRecord();
        playerChampionsLeagueRecord.setPlayer(player);
        playerChampionsLeagueRecord.setChampionsLeagueRound(championsLeagueRound);
        playerChampionsLeagueRecord.setPosition(position);
        playerChampionsLeagueRecord.setSeason(championsLeagueRound.getSeason());
        playerChampionsLeagueRecord.setTeam(team);
        return playerChampionsLeagueRecord;
    }


}
