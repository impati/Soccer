package com.example.soccerleague.SearchService.LeagueRound.GameResult;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundGameResultRequest extends DataTransferObject {
    private Long roundId;
    private Long teamId;
    public LeagueRoundGameResultRequest(Long roundId) {
        this.roundId = roundId;
    }
}
