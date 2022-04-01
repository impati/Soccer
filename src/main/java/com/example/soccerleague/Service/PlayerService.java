package com.example.soccerleague.Service;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;

import java.util.List;

public interface PlayerService {
    void signUp(DataTransferObject dto);
    Player findPlayer(Long id);
    List<Player> findBySearchDto(DataTransferObject searchDto);
    void playerUpdate(Long playerId,DataTransferObject dto);
}
