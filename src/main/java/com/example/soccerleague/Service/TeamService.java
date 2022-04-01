package com.example.soccerleague.Service;

import com.example.soccerleague.domain.Team;

import java.util.List;

public interface TeamService {
    Team searchTeam(Long id);
    List<Team> searchAll();
    List<Team> searchByLeague(Long leagueId);
}
