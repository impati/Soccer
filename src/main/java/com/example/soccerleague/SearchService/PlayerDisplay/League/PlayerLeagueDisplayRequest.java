package com.example.soccerleague.SearchService.PlayerDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerLeagueDisplayRequest extends DataTransferObject {
    private Long playerId;
    private int season;
}
