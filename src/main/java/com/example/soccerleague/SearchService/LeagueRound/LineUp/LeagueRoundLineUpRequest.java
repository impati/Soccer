package com.example.soccerleague.SearchService.LeagueRound.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundLineUpRequest extends DataTransferObject {
    private Long roundId;
}
