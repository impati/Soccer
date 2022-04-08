package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface PlayerEntityRepository extends EntityRepository{
    List<Player> findByName(String name);
    List<Player> findByTeam(Team team);
    List<Player> findByLeague(Long leagueId);
}
