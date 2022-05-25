package com.example.soccerleague.SearchService.DirectorDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorLeagueDisplayResponse extends DataTransferObject {
    private int win;
    private int draw;
    private int lose;
    private int rank;
}
