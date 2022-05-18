package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;

import java.util.List;

public interface TeamEntityRepository extends EntityRepository{
    List<Team> findByLeagueId(Long leagueId);
    List<Team> findByLeague(League league);
    List<Team> findByLeagueTop16(Long leagueId);
}
