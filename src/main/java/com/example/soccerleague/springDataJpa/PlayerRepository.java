package com.example.soccerleague.springDataJpa;


import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> ,PlayerRepositoryQuerydsl{

    @Query("select p from Player p where p.name LIKE %:name%")
    List<Player> findByName(@Param("name") String name);

    List<Player> findByTeam(@Param("team") Team team);
    @Query(value = "select P FROM Player P JOIN P.team T ON T.id = :teamId")
    List<Player> findByTeamId(@Param("teamId")Long teamId);

}
