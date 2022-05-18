package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.RegisterService.GameAvgDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import java.util.List;
import java.util.Optional;

public interface PlayerLeagueRecordEntityRepository extends PlayerRecordEntityRepository{
    Optional<PlayerLeagueRecord> findByLast(int season, Long playerId);
    List<PlayerLeagueRecord> findByRoundAndTeam(Long roundId,Long teamId);
    List<PlayerLeagueRecord> findBySeasonAndPlayer(Round round,Long playerId);
    List<Object[]> findSeasonAndTeam(int season,Long teamId);

    GameAvgDto findByGameAvg();
    Double avgGrade();
}
