package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League,Long> {
}
