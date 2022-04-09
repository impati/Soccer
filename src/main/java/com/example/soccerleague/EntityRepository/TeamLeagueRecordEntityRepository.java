package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;

import java.util.List;
import java.util.Optional;

public interface TeamLeagueRecordEntityRepository extends TeamRecordEntityRepository{
    List<TeamLeagueRecord> findByTeamId(Long teamId);
    List<TeamLeagueRecord> findBySeasonAndTeam(int season, int roundSt, Team team);
    List<TeamLeagueRecord> findBySeasonAndTeamRecent5(int season,int roundSt,Team team);
    Optional<TeamLeagueRecord> findByLastRecord(int season, Long teamId);
    List<TeamLeagueRecord> findByShowDown(Round round , Team teamA, Team teamB);
}
