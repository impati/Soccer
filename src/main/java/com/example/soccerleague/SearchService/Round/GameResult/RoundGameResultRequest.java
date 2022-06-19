package com.example.soccerleague.SearchService.Round.GameResult;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundGameResultRequest extends DataTransferObject {
    private Long roundId;
    private Long teamId;
    public RoundGameResultRequest(Long roundId) {
        this.roundId = roundId;
    }
}
