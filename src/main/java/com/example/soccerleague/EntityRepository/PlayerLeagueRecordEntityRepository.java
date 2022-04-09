package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import java.util.List;
import java.util.Optional;

public interface PlayerLeagueRecordEntityRepository extends PlayerRecordEntityRepository{
    List<PlayerLeagueRecord> findBySeasonAndPlayer(int season , Long playerId);
    List<PlayerLeagueRecord> findBySeasonAndLeague(int season,Long leagueId);
    List<Object[]> findSeasonAndTeam(int season,Long teamId);
    Optional<PlayerLeagueRecord> findByLast(int season, Long playerId);
    List<Object[]> TopPlayerSeasonAndRoundStWithStrategy(Round round, Team team);
}
