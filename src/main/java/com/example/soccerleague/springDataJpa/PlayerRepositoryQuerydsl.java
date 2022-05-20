package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.PlayerSearch.PlayerSearchRequest;
import com.example.soccerleague.domain.Player.Player;

import java.util.List;

public interface PlayerRepositoryQuerydsl {
    List<Player> playerList(PlayerSearchRequest req);
}
