package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundSeasonTeamRequest extends DataTransferObject {
    private Long roundId;
    private Long teamId;
}
