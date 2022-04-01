package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.League;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository {
    void save(League league);
    List<League> findAll();
    Optional<League> findById(Long id);
}
