package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;

import java.util.List;

public interface TeamRepository {
    void save(Team team);
    Team findById(Long id);
    List<Team> findAll();
    List<Team> findByLeagueId(Long leagueId);
    List<Team> findByLeague(League league);
    List<Team> findByLeagueTop16(Long leagueId);
}
