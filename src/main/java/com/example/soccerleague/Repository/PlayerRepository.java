package com.example.soccerleague.Repository;


import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    void save(Player player);
    Player findById(Long id);
    List<Player> findAll();
    List<Player> findByName(String name);
    List<Player> findByTeam(Team team);
}
