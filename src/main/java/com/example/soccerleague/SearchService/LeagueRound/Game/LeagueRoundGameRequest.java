package com.example.soccerleague.SearchService.LeagueRound.Game;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundGameRequest extends DataTransferObject {
    private Long roundId;
}
