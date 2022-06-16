package com.example.soccerleague.SearchService.ChampionsRound;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChampionsRoundInfoRequest extends DataTransferObject {
    private int season;
    private int roundSt;
}
