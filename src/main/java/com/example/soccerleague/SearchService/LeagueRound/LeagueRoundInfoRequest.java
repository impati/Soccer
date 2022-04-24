package com.example.soccerleague.SearchService.LeagueRound;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueRoundInfoRequest extends DataTransferObject {
    private int season;
    private int roundSt;
    private Long leagueId;
}
