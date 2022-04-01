package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;

import java.util.List;
import java.util.Optional;

public interface TeamLeagueRecordRepository {
    void save(TeamLeagueRecord teamLeagueRecord);
    List<TeamLeagueRecord> findByTeamId(Long teamId);
    List<TeamLeagueRecord> findByRoundId(Long roundId);
    List<TeamLeagueRecord> findBySeasonAndTeam(int season,int roundSt,Team team);
    List<TeamLeagueRecord> findBySeasonAndTeamRecent5(int season,int roundSt,Team team);
    List<TeamLeagueRecord> findByShowDown(Round round , Team teamA, Team teamB);
}
