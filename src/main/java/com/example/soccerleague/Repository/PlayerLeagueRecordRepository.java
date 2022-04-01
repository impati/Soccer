package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import java.util.List;

public interface PlayerLeagueRecordRepository {
    void save(PlayerLeagueRecord playerLeagueRecord);
    PlayerLeagueRecord findById(Long id);
    List<PlayerLeagueRecord> findBySeasonAndPlayer(int season , Long playerId);
    List<PlayerLeagueRecord> findByRoundId(Long roundId);
    List<Object[]> TopPlayerSeasonAndRoundStWithStrategy(Round round, Team team);
}
