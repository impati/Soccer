package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.domain.Player.Position;
import lombok.Data;

@Data
public class PlayerSearchResponse {
    private Long playerId;
    private String name ;
    private String teamName;
    private Position position;
}
