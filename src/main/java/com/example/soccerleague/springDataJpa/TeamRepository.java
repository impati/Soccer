package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select t from Team t join t.league l on l.id =:leagueId")
    List<Team> findByLeagueId(@Param("leagueId") Long leagueId);

    @Query("select t from Team t join t.league l on l.id =:leagueId ")
    List<Team> findByLeagueId(@Param("leagueId") Long leagueId, Pageable pageable);

}
